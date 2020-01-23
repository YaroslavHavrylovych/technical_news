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

package com.gmail.yaroslavlancelot.technarium.screens.itemslist.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.yaroslavlancelot.technarium.data.ProviderType.CODEGUIDA
import com.gmail.yaroslavlancelot.technarium.data.ProviderType.DOU
import com.gmail.yaroslavlancelot.technarium.data.ProviderType.TOKAR
import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticlesViewModel
@Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val providers = setOf(CODEGUIDA, DOU, TOKAR)

    fun getArticles() = repository.getArticles(providers)

    fun refresh() {
        viewModelScope.launch(Dispatchers.Default) {
            repository.refreshArticles(providers)
        }
    }
}