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

package com.gmail.yaroslavlancelot.technarium.screens.itemslist.news

import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import com.gmail.yaroslavlancelot.technarium.data.ItemType
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.Post
import com.gmail.yaroslavlancelot.technarium.screens.base.CachedItemsViewModel
import com.gmail.yaroslavlancelot.technarium.settings.AppSettings
import javax.inject.Inject

class NewsViewModel
@Inject constructor(private val repository: DataRepository, private val settings: AppSettings) : CachedItemsViewModel<Post>() {
    override fun loadingStatus() = repository.loadingStatus(ItemType.NEWS)

    override fun updateItem(item: Post) = repository.updateEntity(item)

    override fun getFromRepository() = repository.getNews(settings.getProviders())

    override fun refreshRepository() = repository.refreshNews(settings.getProviders())
}