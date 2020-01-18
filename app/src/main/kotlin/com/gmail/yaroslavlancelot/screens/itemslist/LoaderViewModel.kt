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

package com.gmail.yaroslavlancelot.screens.itemslist

import androidx.annotation.UiThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class LoaderViewModel : ViewModel() {
    private val dataLoaders = ArrayList<DataLoader>()

    protected fun loadingStarted() {
        viewModelScope.launch(Dispatchers.Main) { dataLoaders.forEach { it.dataLoadingStarted() } }
    }

    protected fun loadingDone() {
        viewModelScope.launch(Dispatchers.Main) { dataLoaders.forEach { it.dataLoadingFinished() } }
    }

    @UiThread
    fun addObserver(observer: DataLoader) = dataLoaders.add(observer)

    @UiThread
    fun removeObserver(observer: DataLoader) = dataLoaders.add(observer)

    override fun onCleared() {
        super.onCleared()
        dataLoaders.clear()
    }
}