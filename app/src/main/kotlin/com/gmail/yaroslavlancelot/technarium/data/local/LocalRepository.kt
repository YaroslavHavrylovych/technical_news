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
import com.gmail.yaroslavlancelot.technarium.data.local.items.events.EventPost
import com.gmail.yaroslavlancelot.technarium.data.local.items.openings.OpeningPost
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.Post
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Category
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Experience
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Location
import java.util.*

interface LocalRepository {
    fun getArticles(providers: Set<ProviderType>): LiveData<List<Post>>

    fun getNews(providers: Set<ProviderType>): LiveData<List<Post>>

    fun getOpenings(
        providers: Set<ProviderType>, query: String, category: Category,
        location: Location, experience: Experience
    ): LiveData<List<OpeningPost>>

    fun getSelectedPosts(): LiveData<List<Post>>

    fun getEvents(providers: Set<ProviderType>): LiveData<List<EventPost>>

    fun insertArticles(lst: List<Post>)

    fun insertNews(lst: List<Post>)

    fun insertOpenings(lst: List<OpeningPost>)

    fun insertEvents(lst: List<EventPost>)

    fun updateEntity(entity: Post)

    fun clearHistory(type: ItemType)
}

class LocalRepositoryImpl(private val dao: ItemDao, private val history: HistoryReservable) : LocalRepository {
    override fun getArticles(providers: Set<ProviderType>): LiveData<List<Post>> = dao.getPosts(providers, ItemType.ARTICLE)

    override fun getNews(providers: Set<ProviderType>): LiveData<List<Post>> = dao.getPosts(providers, ItemType.NEWS)

    override fun getOpenings(
        providers: Set<ProviderType>, query: String, category: Category,
        location: Location, experience: Experience
    ): LiveData<List<OpeningPost>> {
        var dbQuery = "SELECT * FROM opening WHERE (selected = 1 OR provider IN (${providers.joinToString(separator = ",") { "'${it.providerName}'" }}))"
        if (category != Category.NONE) dbQuery = "$dbQuery AND category='${category.data}'"
        if (location != Location.NONE) dbQuery = "$dbQuery AND location='${location.data}'"
        if (experience != Experience.NONE) dbQuery = "$dbQuery AND experience='${experience.data}'"
        return if (query.isNotEmpty()) dao.getOpening(
            SimpleSQLiteQuery(
                "$dbQuery AND (description LIKE ? OR query LIKE ?) ORDER BY selected DESC, pub_date DESC",
                arrayOf("%$query%", "%$query%")
            )
        )
        else dao.getOpening(SimpleSQLiteQuery("$dbQuery ORDER BY selected DESC, pub_date DESC"))
    }

    override fun getSelectedPosts(): LiveData<List<Post>> = dao.getSelectedPost()

    override fun getEvents(providers: Set<ProviderType>): LiveData<List<EventPost>> = dao.getEvents(providers)

    override fun insertArticles(lst: List<Post>) = dao.upsertPosts(lst)

    override fun insertNews(lst: List<Post>) = dao.upsertPosts(lst)

    override fun insertOpenings(lst: List<OpeningPost>) = dao.upsertOpenings(lst)

    override fun insertEvents(lst: List<EventPost>) = dao.upsertEvents(lst)

    override fun updateEntity(entity: Post) =
        when (entity) {
            is OpeningPost -> dao.updateOpening(entity)
            is EventPost -> dao.updateEvent(entity)
            else -> dao.updatePost(entity)
        }

    override fun clearHistory(type: ItemType) = dao.clearHistory(type, history.oldestHistory().time)
}

interface HistoryReservable {
    fun oldestHistory(): Date
}