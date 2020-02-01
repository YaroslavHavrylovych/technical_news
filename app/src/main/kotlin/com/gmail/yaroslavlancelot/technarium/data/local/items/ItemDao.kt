/**
 * Copyright (C) 2020 Yaroslav Havrylovych
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gmail.yaroslavlancelot.technarium.data.local.items

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import com.gmail.yaroslavlancelot.technarium.data.ItemType
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.local.items.events.EventEntity
import com.gmail.yaroslavlancelot.technarium.data.local.items.openings.OpeningEntity
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.PostEntity

@Dao
abstract class ItemDao {
    @Query("SELECT * FROM post WHERE type == :type AND provider IN (:providers) ORDER BY pub_date DESC")
    abstract fun getPosts(providers: Set<ProviderType>, type: ItemType): LiveData<List<PostEntity>>

    @Query("SELECT * FROM event WHERE provider IN (:providers) ORDER BY pub_date DESC")
    abstract fun getEvents(providers: Set<ProviderType>): LiveData<List<EventEntity>>

    @Query("SELECT * FROM post WHERE provider IN (:providers) AND selected = 1 ORDER BY pub_date DESC")
    abstract fun getSelectedPost(providers: Set<ProviderType>): LiveData<List<PostEntity>>

    @RawQuery(observedEntities = [OpeningEntity::class])
    abstract fun getOpening(query: SimpleSQLiteQuery): LiveData<List<OpeningEntity>>

    @Update
    abstract fun updatePost(entity: PostEntity)

    @Update
    abstract fun updateOpening(entity: OpeningEntity)

    @Update
    abstract fun updateEvent(entity: EventEntity)

    @Transaction
    open fun upsertPosts(posts: List<PostEntity>) =
        upsertEntities(posts, ::getPosts, ::insertPosts, ::updatePosts) { n -> ArrayList(n) }

    @Transaction
    open fun upsertEvents(events: List<EventEntity>) =
        upsertEntities(events, ::getEvents, ::insertEvents, ::updateEvents) { n -> ArrayList(n) }

    @Transaction
    open fun upsertOpenings(openings: List<OpeningEntity>) =
        upsertEntities(openings, ::getOpenings, ::insertOpenings, ::updateOpenings) { n -> ArrayList(n) }


    private inline fun <reified T : BaseEntity> upsertEntities(
        entities: List<T>,
        get: (List<String>) -> List<T>,
        insert: (List<T>) -> List<Long>,
        update: (List<T>) -> Unit,
        emptyList: (n: Int) -> ArrayList<T>
    ) {
        val insertResult = insert(entities)
        val updateList = emptyList(entities.size)
        //-1 means that the raw wasn't inserted as the item exists
        for (i in insertResult.indices) if (insertResult[i] == -1L) updateList.add(entities[i])
        if (updateList.isEmpty()) return
        val existingEntities = get(updateList.map { it.link })
        val listToUpdate = emptyList(updateList.size)
        for (newEntity in updateList) {
            val existing = existingEntities.find { it.link == newEntity.link } ?: continue
            listToUpdate.add(fromEntities(newEntity, existing))
        }
        update(listToUpdate)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertPosts(posts: List<PostEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertOpenings(openings: List<OpeningEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertEvents(events: List<EventEntity>): List<Long>

    @Query("SELECT * from opening WHERE link in (:links)")
    protected abstract fun getOpenings(links: List<String>): List<OpeningEntity>

    @Query("SELECT * from event WHERE link in (:links)")
    protected abstract fun getEvents(links: List<String>): List<EventEntity>

    @Query("SELECT * from post WHERE link in (:links)")
    protected abstract fun getPosts(links: List<String>): List<PostEntity>

    @Update
    protected abstract fun updateOpenings(openings: List<OpeningEntity>)

    @Update
    protected abstract fun updatePosts(posts: List<PostEntity>)

    @Update
    protected abstract fun updateEvents(posts: List<EventEntity>)

    private inline fun <reified T : BaseEntity> fromEntities(newEntity: T, oldEntity: T): T {
        require(newEntity::class == oldEntity::class)
        if (newEntity is EventEntity) return EventEntity.fromEntities(newEntity, oldEntity as EventEntity) as T
        else if (newEntity is OpeningEntity) return OpeningEntity.fromEntities(newEntity, oldEntity as OpeningEntity) as T
        return PostEntity.fromEntities(newEntity as PostEntity, oldEntity as PostEntity) as T
    }
}
