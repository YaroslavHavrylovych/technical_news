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

    @RawQuery(observedEntities = [OpeningEntity::class])
    abstract fun getOpening(query: SimpleSQLiteQuery): LiveData<List<OpeningEntity>>

    @Query("SELECT * from opening WHERE link in (:links)")
    abstract fun getOpening(links: List<String>): List<OpeningEntity>

    @Update
    abstract fun updateOpenings(openings: List<OpeningEntity>)

    @Update
    abstract fun updatePost(entity: PostEntity)

    @Update
    abstract fun updateOpening(entity: OpeningEntity)

    @Update
    abstract fun updateEvent(entity: EventEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertPosts(posts: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertOpenings(openings: List<OpeningEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertEvents(events: List<EventEntity>)

    @Transaction
    open fun upsertOpenings(openings: List<OpeningEntity>) {
        val insertResult = insertOpenings(openings)
        val updateList = ArrayList<OpeningEntity>()
        //-1 means that the raw wasn't inserted as the item exists
        for (i in insertResult.indices) if (insertResult[i] == -1L) updateList.add(openings[i])
        if (updateList.isEmpty()) return
        val existingEntities = getOpening(updateList.map { it.link })
        val listToUpdate = ArrayList<OpeningEntity>(updateList.size)
        for (newEntity in updateList) {
            val existing = existingEntities.find { it.link == newEntity.link } ?: continue
            listToUpdate.add(OpeningEntity(newEntity, existing))
        }
        updateOpenings(listToUpdate)
    }
}