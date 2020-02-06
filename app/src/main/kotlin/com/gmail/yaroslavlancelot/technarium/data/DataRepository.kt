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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.yaroslavlancelot.technarium.data.local.LocalRepository
import com.gmail.yaroslavlancelot.technarium.data.local.items.events.EventPost
import com.gmail.yaroslavlancelot.technarium.data.local.items.openings.OpeningPost
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.Post
import com.gmail.yaroslavlancelot.technarium.data.network.NetworkRepository
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Category
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Experience
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

interface DataRepository {
    fun refreshArticles(providers: Set<ProviderType>)

    fun refreshNews(providers: Set<ProviderType>)

    fun refreshOpenings(
        providers: Set<ProviderType>,
        query: String = "",
        category: Category = Category.NONE,
        location: Location = Location.NONE,
        experience: Experience = Experience.NONE
    )

    fun refreshEvents(providers: Set<ProviderType>)

    fun getArticles(providers: Set<ProviderType>): LiveData<List<Post>>

    fun getNews(providers: Set<ProviderType>): LiveData<List<Post>>

    fun getSelectedPosts(providers: Set<ProviderType>): LiveData<List<Post>>

    fun getOpenings(
        providers: Set<ProviderType>,
        query: String = "",
        category: Category = Category.NONE,
        location: Location = Location.NONE,
        experience: Experience = Experience.NONE
    ): LiveData<List<OpeningPost>>

    fun getEvents(providers: Set<ProviderType>): LiveData<List<EventPost>>

    fun loadingStatus(): LiveData<LoadingStatus>

    fun updateEntity(entity: Post)

    enum class LoadingStatus {
        NONE, LOADING, LOADED
    }
}


//TODO creation and destroying must be handled in a Activity's (single we have) viewModel. In this case we can stop the job if needed
internal class DataRepositoryImpl(
    private val networkRepo: NetworkRepository,
    private val localRepo: LocalRepository
) : DataRepository, CoroutineScope {
    private val status = MutableLiveData<DataRepository.LoadingStatus>(DataRepository.LoadingStatus.NONE)
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun refreshArticles(providers: Set<ProviderType>) {
        status.postValue(DataRepository.LoadingStatus.LOADING)
        launch {
            localRepo.insertArticles(
                networkRepo.refreshArticles(providers).map {
                    Post(
                        it.link(), ItemType.ARTICLE, it.provider(), it.title(),
                        it.description(), it.date().parseDate(), false
                    )
                })
            status.postValue(DataRepository.LoadingStatus.LOADED)
        }
    }

    override fun refreshNews(providers: Set<ProviderType>) {
        status.postValue(DataRepository.LoadingStatus.LOADING)
        launch {
            localRepo.insertNews(
                networkRepo.refreshNews(providers).map {
                    Post(
                        it.link(), ItemType.NEWS, it.provider(), it.title(),
                        it.description(), it.date().parseDate(), false
                    )
                })
            status.postValue(DataRepository.LoadingStatus.LOADED)
        }
    }

    override fun refreshOpenings(providers: Set<ProviderType>, query: String, category: Category, location: Location, experience: Experience) {
        status.postValue(DataRepository.LoadingStatus.LOADING)
        launch {
            localRepo.insertOpenings(
                networkRepo.refreshOpenings(providers, query, category, location, experience).map {
                    OpeningPost(
                        it.link(), it.provider(), it.title(),
                        it.description(), it.date().parseDate(), false,
                        query, category, location, experience
                    )
                })
            status.postValue(DataRepository.LoadingStatus.LOADED)
        }
    }

    override fun refreshEvents(providers: Set<ProviderType>) {
        status.postValue(DataRepository.LoadingStatus.LOADING)
        launch {
            localRepo.insertEvents(
                networkRepo.refreshEvents(providers).map {
                    EventPost(
                        it.link(), it.provider(), it.title(),
                        it.description(), it.date().parseDate(), false,
                        //TODO event date
                        null, null
                    )
                })
            status.postValue(DataRepository.LoadingStatus.LOADED)
        }
    }

    override fun getArticles(providers: Set<ProviderType>) = localRepo.getArticles(providers)

    override fun getNews(providers: Set<ProviderType>) = localRepo.getNews(providers)

    override fun getSelectedPosts(providers: Set<ProviderType>): LiveData<List<Post>> = localRepo.getSelectedPosts(providers)

    override fun getOpenings(
        providers: Set<ProviderType>, query: String,
        category: Category, location: Location,
        experience: Experience
    ): LiveData<List<OpeningPost>> = localRepo.getOpenings(providers, query, category, location, experience)

    override fun getEvents(providers: Set<ProviderType>) = localRepo.getEvents(providers)

    override fun loadingStatus() = status

    override fun updateEntity(entity: Post) {
        launch {
            localRepo.updateEntity(entity)
        }
    }

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
