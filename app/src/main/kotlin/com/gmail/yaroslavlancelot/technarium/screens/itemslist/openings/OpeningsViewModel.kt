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
import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import com.gmail.yaroslavlancelot.technarium.data.ItemType
import com.gmail.yaroslavlancelot.technarium.data.local.items.openings.OpeningPost
import com.gmail.yaroslavlancelot.technarium.screens.base.CachedItemsViewModel
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Category
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Experience
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.Location
import com.gmail.yaroslavlancelot.technarium.settings.AppSettings
import javax.inject.Inject

class OpeningsViewModel
@Inject constructor(private val repository: DataRepository, private val settings: AppSettings) : CachedItemsViewModel<OpeningPost>() {
    private var queryString = ""
    private var category = Category.NONE
    private var location = Location.NONE
    private var experience = Experience.NONE
    private val filteredLiveDate = MutableLiveData<Boolean>()

    fun getFiltered(): LiveData<Boolean> = filteredLiveDate

    fun isFiltered() = category != Category.NONE || location != Location.NONE || experience != Experience.NONE || queryString.isNotEmpty()

    override fun getFromRepository() = repository.getOpenings(
        settings.getProviders(), queryString, category, location, experience
    )

    override fun refreshRepository() = repository.refreshOpenings(
        settings.getProviders(), queryString, category, location, experience
    )

    override fun refresh() {
        filteredLiveDate.postValue(isFiltered())
        super.refresh()
    }

    override fun loadingStatus() = repository.loadingStatus(ItemType.OPENING)

    override fun updateItem(item: OpeningPost) = repository.updateEntity(item)

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
}
