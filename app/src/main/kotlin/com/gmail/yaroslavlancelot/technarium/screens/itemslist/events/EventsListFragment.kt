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

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.data.local.items.events.EventPost
import com.gmail.yaroslavlancelot.technarium.screens.base.ItemsViewModel
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.BaseItemsListFragment
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.ItemsListAdapter

class EventsListFragment : BaseItemsListFragment<EventPost>() {
    private val viewModel: EventsViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun getViewModel(): ItemsViewModel<EventPost> = viewModel

    override fun onItemClicked(item: EventPost) {
        view?.findNavController()?.navigate(
            EventsListFragmentDirections.actionEventToPreview(item.link, item.title)
        )
    }

    override fun listAdapterBuilder() = ItemsListAdapter.Builder<EventPost>()
        .imageSelector { selected -> if (selected) R.drawable.ic_pinned else R.drawable.ic_pin }
}