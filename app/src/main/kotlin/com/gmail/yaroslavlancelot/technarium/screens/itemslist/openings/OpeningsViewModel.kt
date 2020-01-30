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

package com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import com.gmail.yaroslavlancelot.technarium.data.local.items.openings.OpeningEntity
import com.gmail.yaroslavlancelot.technarium.screens.base.ItemsViewModel
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Category
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Experience
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Location
import javax.inject.Inject

class OpeningsViewModel
@Inject constructor(private val repository: DataRepository) : ViewModel(), ItemsViewModel<OpeningEntity> {
    private val providers = setOf(ProviderType.CODEGUIDA, ProviderType.DOU, ProviderType.TOKAR, ProviderType.PINGVIN)
    private var queryString = ""
    private var category = Category.NONE
    private var location = Location.NONE
    private var experience = Experience.NONE
    private val databaseObserver = DatabaseObserver()
    private var databaseLiveData: LiveData<List<OpeningEntity>>? = null
    var viewModelLiveData = MutableLiveData<List<OpeningEntity>>()

    override fun getItems() = viewModelLiveData

    override fun refresh() {
        databaseLiveData?.removeObserver(databaseObserver)
        databaseLiveData = repository.getOpenings(
            providers, queryString,
            category, location, experience
        )
        databaseLiveData?.observeForever(databaseObserver)
        repository.refreshOpenings(
            providers, queryString,
            category, location, experience
        )
    }

    override fun onCleared() {
        super.onCleared()
        databaseLiveData?.removeObserver(databaseObserver)
    }

    override fun loadingStatus() = repository.loadingStatus()

    override fun updateItem(item: OpeningEntity) = repository.updateEntity(item)

    fun applyFilter(
        queryString: String,
        category: Category,
        location: Location,
        exp: Experience
    ) {
        this.queryString = queryString
        this.category = category
        this.location = location
        this.experience = exp
        refresh()
    }

    fun getSearchQuery() = queryString
    fun getCategory() = category
    fun getLocation() = location
    fun getExperience() = experience

    private inner class DatabaseObserver : Observer<List<OpeningEntity>> {
        override fun onChanged(lst: List<OpeningEntity>?) = viewModelLiveData.postValue(lst)
    }
}
