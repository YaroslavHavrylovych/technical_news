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

package com.gmail.yaroslavlancelot.technarium.data.local

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.gmail.yaroslavlancelot.technarium.data.ItemType
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.local.items.ItemDao
import com.gmail.yaroslavlancelot.technarium.data.local.items.events.EventEntity
import com.gmail.yaroslavlancelot.technarium.data.local.items.openings.OpeningEntity
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.PostEntity
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Category
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Experience
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Location
import kotlin.math.exp

interface LocalRepository {
    fun getArticles(providers: Set<ProviderType>): LiveData<List<PostEntity>>

    fun getNews(providers: Set<ProviderType>): LiveData<List<PostEntity>>

    fun getOpenings(
        providers: Set<ProviderType>, query: String, category: Category,
        location: Location, experience: Experience
    ): LiveData<List<OpeningEntity>>

    fun getEvents(providers: Set<ProviderType>): LiveData<List<EventEntity>>

    fun insertArticles(lst: List<PostEntity>)

    fun insertNews(lst: List<PostEntity>)

    fun insertOpenings(lst: List<OpeningEntity>)

    fun insertEvents(lst: List<EventEntity>)

    fun updateEntity(entity: PostEntity)
}

class LocalRepositoryImpl(private val dao: ItemDao) : LocalRepository {
    override fun getArticles(providers: Set<ProviderType>): LiveData<List<PostEntity>> = dao.getPosts(providers, ItemType.ARTICLE)

    override fun getNews(providers: Set<ProviderType>): LiveData<List<PostEntity>> = dao.getPosts(providers, ItemType.NEWS)

    override fun getOpenings(
        providers: Set<ProviderType>, query: String, category: Category,
        location: Location, experience: Experience
    ): LiveData<List<OpeningEntity>> {
        var dbQuery = "SELECT * FROM opening WHERE provider IN (${providers.joinToString(separator = ",") { "'${it.providerName}'" }})"
        if (category != Category.NONE) dbQuery = "$dbQuery AND category='${category.data}'"
        if (location != Location.NONE) dbQuery = "$dbQuery AND location='${location.data}'"
        if (experience != Experience.NONE) dbQuery = "$dbQuery AND experience='${experience.data}'"
        return if (query.isNotEmpty()) dao.getOpening(SimpleSQLiteQuery("$dbQuery AND (description LIKE ? OR query LIKE ?)", arrayOf(query, query)))
        else dao.getOpening(SimpleSQLiteQuery(dbQuery))
    }

    override fun getEvents(providers: Set<ProviderType>): LiveData<List<EventEntity>> = dao.getEvents(providers)

    override fun insertArticles(lst: List<PostEntity>) = dao.insertPosts(lst)

    override fun insertNews(lst: List<PostEntity>) = dao.insertPosts(lst)

    override fun insertOpenings(lst: List<OpeningEntity>) = dao.upsertOpenings(lst)

    override fun insertEvents(lst: List<EventEntity>) = dao.insertEvents(lst)

    override fun updateEntity(entity: PostEntity) =
        when (entity) {
            is OpeningEntity -> dao.updateOpening(entity)
            is EventEntity -> dao.updateEvent(entity)
            else -> dao.updatePost(entity)
        }
}