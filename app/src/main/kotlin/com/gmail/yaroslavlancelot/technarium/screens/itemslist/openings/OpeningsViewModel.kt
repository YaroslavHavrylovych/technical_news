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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Category
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Experience
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class OpeningsViewModel
@Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val providers = setOf(ProviderType.CODEGUIDA, ProviderType.DOU, ProviderType.TOKAR)
    private var queryString = ""
    private var category = Category.NONE
    private var location = Location.NONE
    private var experience = Experience.NONE

    fun getOpenings() = repository.getOpenings(providers, HashMap())

    fun refresh() {
        viewModelScope.launch(Dispatchers.Default) {
            repository.refreshOpenings(providers, HashMap())
        }
    }

//    fun reload() {
//        val filters = HashMap<String, String>()
//        if (queryString.isNotEmpty()) filters["search"] = queryString
//        if (category != Category.NONE) filters["category"] = category.data
//        if (location != Location.NONE) filters["city"] = location.data
//        if (experience != Experience.NONE) filters["city"] = experience.data
//        refresh()
//    }

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
