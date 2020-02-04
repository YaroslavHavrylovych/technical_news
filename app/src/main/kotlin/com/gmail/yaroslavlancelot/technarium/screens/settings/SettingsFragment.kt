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

package com.gmail.yaroslavlancelot.technarium.screens.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseFragment
import com.gmail.yaroslavlancelot.technarium.settings.AppSettings
import com.gmail.yaroslavlancelot.technarium.settings.HistoryStorage
import kotlinx.android.synthetic.main.lt_settings_fragment.history_spinner
import java.lang.IllegalStateException
import javax.inject.Inject

class SettingsFragment : BaseFragment() {
    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId() = R.layout.lt_settings_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        history_spinner.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.settings_history_values))
        history_spinner.setSelection(appSettings.historyStorage.ordinal + 1)
        history_spinner.onItemSelectedListener = HistorySpinnerListener()
    }

    private inner class HistorySpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) = throw IllegalStateException("Nothing is not a value of this spinner")

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            appSettings.historyStorage = when (id) {
                1L -> HistoryStorage.FEW_MONTHS
                2L -> HistoryStorage.HALF_A_YEAR
                3L -> HistoryStorage.YEAR
                else -> appSettings.historyStorage
            }
        }

    }
    //TODO change theme
    //TODO change theme provider
    //TODO change items appear animation
    //TODO how long should we hold cache (1 day, 2 days, week)
}