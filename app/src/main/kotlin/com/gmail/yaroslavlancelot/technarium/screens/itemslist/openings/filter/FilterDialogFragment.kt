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
import android.widget.ArrayAdapter
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
    private val categories: Array<String> by lazy { resources.getStringArray(R.array.opening_category) }
    private val locations: Array<String> by lazy { resources.getStringArray(R.array.opening_location) }
    private val experience: Array<String> by lazy { resources.getStringArray(R.array.opening_experience) }

    override fun getLayoutId() = R.layout.lt_filter_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apply_button.setOnClickListener(::onApplyFilterClicked)
        clear_button.setOnClickListener(::onClearFilterClicked)
        initFields()
    }

    private fun initFields() {
        search_text_view.setText(viewModel.getSearchQuery())
        //category
        category_spinner.setAdapter(ArrayAdapter(context!!, R.layout.lt_spinner_item, categories))
        if (viewModel.getCategory() != Category.NONE) category_spinner.setText(categories[viewModel.getCategory().ordinal], false)
        //location
        location_spinner.setAdapter(ArrayAdapter(context!!, R.layout.lt_spinner_item, locations))
        if (viewModel.getLocation() != Location.NONE) location_spinner.setText(locations[viewModel.getLocation().ordinal], false)
        //experience
        experience_spinner.setAdapter(ArrayAdapter(context!!, R.layout.lt_spinner_item, experience))
        if (viewModel.getExperience() != Experience.NONE) experience_spinner.setText(experience[viewModel.getCategory().ordinal], false)
    }

    private fun onApplyFilterClicked(@Suppress("UNUSED_PARAMETER") view: View) {
        viewModel.applyFilter(
            search_text_view.text.toString(),
            Category.values()[if (category_spinner.text.toString() == "") 0 else categories.indexOf(category_spinner.text.toString())],
            Location.values()[if (location_spinner.text.toString() == "") 0 else locations.indexOf(location_spinner.text.toString())],
            Experience.values()[if (experience_spinner.text.toString() == "") 0 else experience.indexOf(location_spinner.text.toString())]
        )
        dismiss()
    }

    private fun onClearFilterClicked(@Suppress("UNUSED_PARAMETER") view: View) {
        search_text_view.text?.clear()
        category_spinner.setText("", false)
        location_spinner.setText("", false)
        experience_spinner.setText("", false)
    }
}
