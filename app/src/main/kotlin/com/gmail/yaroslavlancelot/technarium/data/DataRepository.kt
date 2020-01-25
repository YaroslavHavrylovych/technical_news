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

package com.gmail.yaroslavlancelot.technarium.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.gmail.yaroslavlancelot.technarium.data.local.LocalRepository
import com.gmail.yaroslavlancelot.technarium.data.local.items.events.EventEntity
import com.gmail.yaroslavlancelot.technarium.data.local.items.openings.OpeningEntity
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.PostEntity
import com.gmail.yaroslavlancelot.technarium.data.network.NetworkRepository
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

interface DataRepository {
    //TODO refresh has to be inside the repo, not a suspend function
    @WorkerThread
    suspend fun refreshArticles(providers: Set<ProviderType>)

    @WorkerThread
    suspend fun refreshNews(providers: Set<ProviderType>)

    @WorkerThread
    suspend fun refreshOpenings(providers: Set<ProviderType>, filter: Map<String, String>)

    @WorkerThread
    suspend fun refreshEvents(providers: Set<ProviderType>)

    fun getArticles(providers: Set<ProviderType>): LiveData<List<PostEntity>>

    fun getNews(providers: Set<ProviderType>): LiveData<List<PostEntity>>

    fun getOpenings(providers: Set<ProviderType>, filter: Map<String, String>): LiveData<List<OpeningEntity>>

    fun getEvents(providers: Set<ProviderType>): LiveData<List<EventEntity>>
}

internal class DataRepositoryImpl(
    private val networkRepo: NetworkRepository,
    private val localRepo: LocalRepository
) : DataRepository {

    override suspend fun refreshArticles(providers: Set<ProviderType>) =
        localRepo.insertArticles(
            networkRepo.refreshArticles(providers).map {
                PostEntity(
                    it.link(), ItemType.ARTICLE, it.provider(), it.title(),
                    it.description(), it.date().parseDate(), false
                )
            })

    override suspend fun refreshNews(providers: Set<ProviderType>) =
        localRepo.insertNews(
            networkRepo.refreshNews(providers).map {
                PostEntity(
                    it.link(), ItemType.NEWS, it.provider(), it.title(),
                    it.description(), it.date().parseDate(), false
                )
            })

    override suspend fun refreshOpenings(providers: Set<ProviderType>, filter: Map<String, String>) =
        localRepo.insertOpenings(
            networkRepo.refreshOpenings(providers, filter).map {
                OpeningEntity(
                    it.link(), ItemType.NEWS, it.provider(), it.title(),
                    it.description(), it.date().parseDate(), false,
                    //TODO filters
                    null, null, null, null
                )
            })

    override suspend fun refreshEvents(providers: Set<ProviderType>) =
        localRepo.insertEvents(
            networkRepo.refreshEvents(providers).map {
                EventEntity(
                    it.link(), ItemType.NEWS, it.provider(), it.title(),
                    it.description(), it.date().parseDate(), false,
                    //TODO event date
                    null, null
                )
            })

    override fun getArticles(providers: Set<ProviderType>) = localRepo.getArticles(providers)

    override fun getNews(providers: Set<ProviderType>) = localRepo.getNews(providers)

    override fun getOpenings(providers: Set<ProviderType>, filter: Map<String, String>) = localRepo.getOpenings(providers, filter)

    override fun getEvents(providers: Set<ProviderType>) = localRepo.getEvents(providers)

    private fun String.parseDate() = parseDate(this)

    companion object {
        //Mon, 16 Dec 2019 07:00:43 +0000
        private val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)

        fun parseDate(strDate: String): Date {
            var date = Date()
            try {
                date = dateFormat.parse(strDate)
            } catch (ex: ParseException) {
                Timber.e(ex)
            }
            return date
        }
    }
}
