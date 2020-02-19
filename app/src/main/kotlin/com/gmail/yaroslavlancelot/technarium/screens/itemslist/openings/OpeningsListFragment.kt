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

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.data.local.items.openings.OpeningPost
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseActivity
import com.gmail.yaroslavlancelot.technarium.screens.base.ItemsViewModel
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.BaseItemsListFragment
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.ItemsListAdapter
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter.FilterDialogFragment
import com.gmail.yaroslavlancelot.technarium.utils.extensions.observe
import kotlinx.android.synthetic.main.lt_openings_fragment.container
import kotlinx.android.synthetic.main.lt_openings_fragment.filter_button


class OpeningsListFragment : BaseItemsListFragment<OpeningPost>() {
    private val viewModel: OpeningsViewModel by viewModels(
        ownerProducer = { activity as BaseActivity },
        factoryProducer = { viewModelFactory })

    override fun getViewModel(): ItemsViewModel<OpeningPost> = viewModel

    override fun getLayoutId() = R.layout.lt_openings_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filter_button.setOnClickListener(::onFilterClicked)
        observe(viewModel.getFiltered()) { filterApplied ->
            updateFabColor(filterApplied ?: false)
        }
    }

    private fun updateFabColor(filterApplied: Boolean) {
        val color = ContextCompat.getColor(
            requireContext(),
            if (filterApplied) R.color.filtered else R.color.accent
        )
        filter_button.backgroundTintList = ColorStateList.valueOf(color)
    }

    override fun onItemClicked(item: OpeningPost) {
        view?.findNavController()?.navigate(
            OpeningsListFragmentDirections.actionOpeningToPreview(item.link)
        )
    }

    override fun constructBuilder() = ItemsListAdapter.Builder<OpeningPost>()
        .imageSelector { selected -> if (selected) R.drawable.ic_pinned else R.drawable.ic_pin }
        .itemClick(::onItemClicked)
        .selectClick(::onSelectClicked)

    private fun onFilterClicked(@Suppress("UNUSED_PARAMETER") view: View) {
        val x = view.x
        val y = view.y
        view
            .animate()
            .setDuration(600)
            .translationX(container.x - container.width / 2 + filter_button.width)
            .translationY(container.y - container.height / 2 + filter_button.height)
            .withEndAction {
                view.x = x
                view.y = y
                view.visibility = View.INVISIBLE
                view.postDelayed({ view.visibility = View.VISIBLE }, 1000)
                val dialog = FilterDialogFragment()
                parentFragmentManager.beginTransaction()
                    .add(dialog, "filter_fragment")
                    .commit()
            }
    }
}