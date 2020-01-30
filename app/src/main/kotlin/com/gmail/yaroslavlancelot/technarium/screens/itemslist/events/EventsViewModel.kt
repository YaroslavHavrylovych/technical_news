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

package com.gmail.yaroslavlancelot.technarium.screens.itemslist.events

import androidx.lifecycle.ViewModel
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import com.gmail.yaroslavlancelot.technarium.data.local.items.events.EventEntity
import com.gmail.yaroslavlancelot.technarium.screens.base.ItemsViewModel
import javax.inject.Inject

class EventsViewModel
@Inject constructor(private val repository: DataRepository) : ViewModel(), ItemsViewModel<EventEntity> {
    private val providers = setOf(ProviderType.CODEGUIDA, ProviderType.DOU, ProviderType.TOKAR, ProviderType.PINGVIN)

    override fun getItems() = repository.getEvents(providers)

    override fun refresh() = repository.refreshEvents(providers)

    override fun loadingStatus() = repository.loadingStatus()

    override fun updateItem(item: EventEntity) = repository.updateEntity(item)
}
