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

package com.gmail.yaroslavlancelot.screens.itemslist.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.yaroslavlancelot.data.ProviderType.CODEGUIDA
import com.gmail.yaroslavlancelot.data.ProviderType.TOKAR
import com.gmail.yaroslavlancelot.data.ProviderType.DOU
import com.gmail.yaroslavlancelot.data.network.items.ItemsRepository
import com.gmail.yaroslavlancelot.data.network.items.IItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel
@Inject constructor(private val repository: ItemsRepository) : ViewModel() {
    private val news: MutableLiveData<List<IItem>> = MutableLiveData()

    fun getNews(): LiveData<List<IItem>> {
        viewModelScope.launch {
            news.value = repository.loadNews(setOf(TOKAR, DOU, CODEGUIDA))
        }
        return news
    }
}