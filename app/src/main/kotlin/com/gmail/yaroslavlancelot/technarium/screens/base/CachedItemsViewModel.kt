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

package com.gmail.yaroslavlancelot.technarium.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.gmail.yaroslavlancelot.technarium.data.DataRepository

abstract class CachedItemsViewModel<T> : ViewModel(), ItemsViewModel<T>, ExtendableItems by DefaultExtendableItems() {
    private val databaseObserver = DatabaseObserver()
    private var databaseLiveData: LiveData<List<T>>? = null
    private val viewModelLiveData = MutableLiveData<List<T>>()

    override fun getItems() = viewModelLiveData

    override fun retrieve() {
        databaseLiveData?.removeObserver(databaseObserver)
        databaseLiveData = getFromRepository()
        databaseLiveData?.observeForever(databaseObserver)
    }

    override fun refresh() {
        retrieve()
        refreshRepository()
    }

    override fun onCleared() {
        super.onCleared()
        databaseLiveData?.removeObserver(databaseObserver)
    }

    protected abstract fun getFromRepository(): LiveData<List<T>>

    protected abstract fun refreshRepository()

    private inner class DatabaseObserver : Observer<List<T>> {
        override fun onChanged(lst: List<T>?) = viewModelLiveData.postValue(lst)
    }
}

interface ItemsViewModel<T> : ExtendableItems {
    fun getItems(): LiveData<List<T>>

    fun retrieve()

    fun refresh()

    fun loadingStatus(): LiveData<DataRepository.LoadingStatus>

    fun updateItem(item: T)
}

class DefaultExtendableItems : ExtendableItems {
    private var extendedItem: String? = null

    override fun getExtended() = extendedItem

    override fun setExtended(id: String?) {
        extendedItem = id
    }
}

interface ExtendableItems {
    fun getExtended(): String?

    fun setExtended(id: String?)
}