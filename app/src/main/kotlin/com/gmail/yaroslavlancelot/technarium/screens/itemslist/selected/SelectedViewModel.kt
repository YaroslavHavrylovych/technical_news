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

package com.gmail.yaroslavlancelot.technarium.screens.itemslist.selected

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.yaroslavlancelot.technarium.data.ProviderType.CODEGUIDA
import com.gmail.yaroslavlancelot.technarium.data.ProviderType.DOU
import com.gmail.yaroslavlancelot.technarium.data.ProviderType.TOKAR
import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import com.gmail.yaroslavlancelot.technarium.data.ItemType
import com.gmail.yaroslavlancelot.technarium.data.ProviderType.PINGVIN
import com.gmail.yaroslavlancelot.technarium.data.local.items.posts.Post
import com.gmail.yaroslavlancelot.technarium.screens.base.ItemsViewModel
import java.util.*
import javax.inject.Inject

class SelectedViewModel
@Inject constructor(private val repository: DataRepository) : ViewModel(), ItemsViewModel<Post> {
    private val providers = setOf(CODEGUIDA, DOU, TOKAR, PINGVIN)
    private val vmLoadingStatus = MutableLiveData<DataRepository.LoadingStatus>()
    private val statuses: EnumMap<ItemType, DataRepository.LoadingStatus> = EnumMap(ItemType::class.java)

    override fun getItems() = repository.getSelectedPosts(providers)

    init {
        statuses[ItemType.ARTICLE] = DataRepository.LoadingStatus.NONE
        statuses[ItemType.NEWS] = DataRepository.LoadingStatus.NONE
        repository.loadingStatus(ItemType.ARTICLE).observeForever(DatabaseObserver(ItemType.ARTICLE))
        repository.loadingStatus(ItemType.NEWS).observeForever(DatabaseObserver(ItemType.NEWS))
    }

    override fun refresh() {
    }

    override fun loadingStatus() = vmLoadingStatus

    override fun updateItem(item: Post) = repository.updateEntity(item)

    private inner class DatabaseObserver(val itemType: ItemType) : androidx.lifecycle.Observer<DataRepository.LoadingStatus> {
        override fun onChanged(st: DataRepository.LoadingStatus?) {
            statuses[itemType] = st
            val nextStatus: DataRepository.LoadingStatus = when {
                statuses.containsValue(DataRepository.LoadingStatus.LOADING) -> DataRepository.LoadingStatus.LOADING
                statuses.containsValue(DataRepository.LoadingStatus.NONE) -> DataRepository.LoadingStatus.NONE
                else -> DataRepository.LoadingStatus.LOADED
            }
            vmLoadingStatus.postValue(nextStatus)
        }
    }
}