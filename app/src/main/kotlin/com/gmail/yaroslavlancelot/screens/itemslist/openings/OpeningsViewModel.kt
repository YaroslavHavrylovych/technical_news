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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.yaroslavlancelot.data.ProviderType
import com.gmail.yaroslavlancelot.data.network.items.IItem
import com.gmail.yaroslavlancelot.data.network.items.ItemsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class OpeningsViewModel
@Inject constructor(private val repository: ItemsRepository) : ViewModel() {
    private val openings: MutableLiveData<List<IItem>> = MutableLiveData()
    private val filters = HashMap<String, String>()

    private fun getOpenings(): LiveData<List<IItem>> {
        viewModelScope.launch {
            openings.value = repository.loadOpenings(setOf(ProviderType.DOU), filters)
        }
        return openings
    }

    fun getFiltered(
        category: Category = Category.NONE,
        city: City = City.NONE,
        expLow: Int = NO_EXP,
        expHigh: Int = expLow + 1
    ): LiveData<List<IItem>> {
        filters.clear()
        if (category != Category.NONE) filters["category"] = category.filter
        if (city != City.NONE) filters["city"] = city.filter
        if (expLow != -1) filters["exp"] = "$expLow-$expHigh"
        return getOpenings()
    }

    enum class Category(val filter: String) {
        NONE(""), ANDROID("Android"), IOS("iOS/macOS")
    }

    enum class City(val filter: String) {
        NONE(""), KYIV("Київ"), KHARKIV("Харків")
    }

    companion object {
        val NO_EXP = -1
    }
}