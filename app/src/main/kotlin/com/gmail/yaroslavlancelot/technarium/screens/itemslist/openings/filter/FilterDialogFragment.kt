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

package com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseActivity
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseDialogFragment
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.OpeningsViewModel
import kotlinx.android.synthetic.main.lt_filter_fragment.apply_button
import kotlinx.android.synthetic.main.lt_filter_fragment.category_spinner
import kotlinx.android.synthetic.main.lt_filter_fragment.clear_button
import kotlinx.android.synthetic.main.lt_filter_fragment.experience_spinner
import kotlinx.android.synthetic.main.lt_filter_fragment.location_spinner
import kotlinx.android.synthetic.main.lt_filter_fragment.search_text_view

class FilterDialogFragment : BaseDialogFragment() {
    private val viewModel: OpeningsViewModel by viewModels(
        ownerProducer = { activity as BaseActivity },
        factoryProducer = { viewModelFactory })

    override fun getLayoutId() = R.layout.lt_filter_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apply_button.setOnClickListener(::onApplyFilterClicked)
        clear_button.setOnClickListener(::onClearFilterClicked)
        initFields()
    }

    private fun initFields() {
        search_text_view.setText(viewModel.getSearchQuery())
        category_spinner.setSelection(viewModel.getCategory().ordinal)
        location_spinner.setSelection(viewModel.getLocation().ordinal)
        experience_spinner.setSelection(viewModel.getExperience().ordinal)
    }

    private fun onApplyFilterClicked(@Suppress("UNUSED_PARAMETER") view: View) {
        viewModel.applyFilter(
            search_text_view.text.toString(),
            Category.values()[category_spinner.selectedItemPosition],
            Location.values()[location_spinner.selectedItemPosition],
            Experience.values()[experience_spinner.selectedItemPosition]
        )
        //TODO filters
        viewModel.refresh()
        dismiss()
    }

    private fun onClearFilterClicked(@Suppress("UNUSED_PARAMETER") view: View) {
        search_text_view.text?.clear()
        category_spinner.setSelection(0)
        location_spinner.setSelection(0)
        experience_spinner.setSelection(0)
    }
}
