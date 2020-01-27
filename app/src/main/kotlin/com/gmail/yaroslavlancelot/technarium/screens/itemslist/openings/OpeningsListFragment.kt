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

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.data.local.items.openings.OpeningEntity
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseActivity
import com.gmail.yaroslavlancelot.technarium.screens.base.ItemsViewModel
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.BaseItemsListFragment
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.ItemsListAdapter
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.FilterDialogFragment
import kotlinx.android.synthetic.main.lt_items_fragment.news_recycler_view
import kotlinx.android.synthetic.main.lt_openings_fragment.filter_button

class OpeningsListFragment : BaseItemsListFragment<OpeningEntity>() {
    private val viewModel: OpeningsViewModel by viewModels(
        ownerProducer = { activity as BaseActivity },
        factoryProducer = { viewModelFactory })

    override fun getViewModel(): ItemsViewModel<OpeningEntity> = viewModel

    override fun getLayoutId() = R.layout.lt_openings_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filter_button.setOnClickListener(::onFilterClicked)
    }

    override fun onItemClicked(item: OpeningEntity) {
        view?.findNavController()?.navigate(
            OpeningsListFragmentDirections.actionOpeningToPreview(item.link)
        )
    }

    override fun setAdapter() {
        news_recycler_view.adapter = ItemsListAdapter(ArrayList(), ::onItemClicked)
    }

    private fun onFilterClicked(@Suppress("UNUSED_PARAMETER") view: View) {
        parentFragmentManager.beginTransaction()
            .add(FilterDialogFragment(), "filter_fragment")
            .commit()
    }
}