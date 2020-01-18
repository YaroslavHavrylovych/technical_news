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

package com.gmail.yaroslavlancelot.screens.itemslist.openings

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.yaroslavlancelot.data.ProviderType
import com.gmail.yaroslavlancelot.data.network.items.IItem
import com.gmail.yaroslavlancelot.data.network.items.ItemsRepository
import com.gmail.yaroslavlancelot.screens.itemslist.LoaderViewModel
import com.gmail.yaroslavlancelot.screens.itemslist.openings.filter.Category
import com.gmail.yaroslavlancelot.screens.itemslist.openings.filter.Experience
import com.gmail.yaroslavlancelot.screens.itemslist.openings.filter.Location
import kotlinx.coroutines.launch
import javax.inject.Inject

class OpeningsViewModel
@Inject constructor(private val repository: ItemsRepository) : LoaderViewModel() {
    private val openings: MutableLiveData<List<IItem>> = MutableLiveData()
    private var queryString = ""
    private var category = Category.NONE
    private var location = Location.NONE
    private var experience = Experience.NONE

    fun getOpenings(): LiveData<List<IItem>> {
        return openings
    }

    @UiThread
    fun reload() {
        val filters = HashMap<String, String>()
        if (queryString.isNotEmpty()) filters["search"] = queryString
        if (category != Category.NONE) filters["category"] = category.data
        if (location != Location.NONE) filters["city"] = location.data
        if (experience != Experience.NONE) filters["city"] = experience.data
        viewModelScope.launch {
            loadingStarted()
            openings.value = repository.loadOpenings(setOf(ProviderType.DOU), filters)
            loadingDone()
        }
    }

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
    }

    fun getSearchQuery() = queryString
    fun getCategory() = category
    fun getLocation() = location
    fun getExperience() = experience
}
