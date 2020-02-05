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
import android.widget.RadioGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseFragment
import com.gmail.yaroslavlancelot.technarium.settings.AppSettings
import com.gmail.yaroslavlancelot.technarium.settings.HistoryStorage
import kotlinx.android.synthetic.main.lt_history_selection_fragment.view.radio_group
import kotlinx.android.synthetic.main.lt_settings_fragment.settings_list
import javax.inject.Inject


class SettingsFragment : BaseFragment() {
    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId() = R.layout.lt_settings_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings_list.layoutManager = LinearLayoutManager(context)
        settings_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        settings_list.adapter = SettingsAdapter(listOf(Setting.HISTORY, Setting.DATA_PROVIDERS), ::onSettingSelected)
    }

    private fun onSettingSelected(setting: Setting) {
        when (setting) {
            Setting.HISTORY -> {
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                dialogBuilder.setTitle(R.string.settings_history_title)
                val dialogView: View = layoutInflater.inflate(R.layout.lt_history_selection_fragment, null)
                preselectHistory(dialogView.radio_group)
                dialogView.radio_group.setOnCheckedChangeListener(::onHistoryChanged)
                dialogBuilder.setView(dialogView)
                dialogBuilder.create().show()
            }
            Setting.DATA_PROVIDERS -> {
                //TODO not implemented yet
            }
        }
    }

    private fun preselectHistory(radioGroup: RadioGroup) {
        radioGroup.check(
            when (appSettings.historyStorage) {
                HistoryStorage.FEW_MONTHS -> R.id.a_few_months_button
                HistoryStorage.HALF_A_YEAR -> R.id.half_a_year_button
                HistoryStorage.YEAR -> R.id.year_button
            }
        )
    }

    private fun onHistoryChanged(@Suppress("UNUSED_PARAMETER") group: RadioGroup, @IdRes checkedId: Int) {
        appSettings.historyStorage = when (checkedId) {
            R.id.a_few_months_button -> HistoryStorage.FEW_MONTHS
            R.id.half_a_year_button -> HistoryStorage.HALF_A_YEAR
            R.id.year_button -> HistoryStorage.YEAR
            else -> appSettings.historyStorage
        }
    }

    //TODO change theme
    //TODO change theme provider
    //TODO change items appear animation
    //TODO how long should we hold cache (1 day, 2 days, week)
}