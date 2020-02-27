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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.yaroslavlancelot.technarium.BuildConfig
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseFragment
import com.gmail.yaroslavlancelot.technarium.settings.AppSettings
import com.gmail.yaroslavlancelot.technarium.settings.HistoryStorage
import kotlinx.android.synthetic.main.lt_history_selection_fragment.view.radio_group
import kotlinx.android.synthetic.main.lt_providers_selection_fragment.view.codeguida
import kotlinx.android.synthetic.main.lt_providers_selection_fragment.view.container
import kotlinx.android.synthetic.main.lt_providers_selection_fragment.view.dou
import kotlinx.android.synthetic.main.lt_providers_selection_fragment.view.pingvin
import kotlinx.android.synthetic.main.lt_providers_selection_fragment.view.tokar
import kotlinx.android.synthetic.main.lt_settings_fragment.settings_list
import java.util.*
import javax.inject.Inject

//TODO this screen must have ViewModel and remove most of the app logic he does
class SettingsFragment : BaseFragment() {
    @Inject
    lateinit var appSettings: AppSettings

    override fun getLayoutId() = R.layout.lt_settings_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings_list.layoutManager = LinearLayoutManager(context)
        settings_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        settings_list.adapter = SettingsAdapter(
            listOf(Setting.HISTORY, Setting.DATA_PROVIDERS, Setting.FEEDBACK, Setting.VERSION),
            ::onSettingSelected
        )
    }

    private var lastClick = Date().time
    private var clicks = 0
    private fun onSettingSelected(setting: Setting) {
        when (setting) {
            Setting.HISTORY -> {
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                dialogBuilder.setTitle(R.string.settings_history_title)
                val dialogView: View = layoutInflater.inflate(R.layout.lt_history_selection_fragment, null)
                initHistory(dialogView.radio_group)
                dialogView.radio_group.setOnCheckedChangeListener(::onHistoryChanged)
                dialogBuilder.setView(dialogView)
                dialogBuilder.create().show()
            }
            Setting.DATA_PROVIDERS -> {
                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                dialogBuilder.setTitle(R.string.settings_provider_title)
                val dialogView: View = layoutInflater.inflate(R.layout.lt_providers_selection_fragment, null)
                initProviders(dialogView.container)
                dialogBuilder.setView(dialogView)
                dialogBuilder.create().show()
            }
            Setting.VERSION -> {
                val time = Date().time
                if (time - lastClick < 1000) clicks++
                else clicks = 0
                lastClick = time
                when (clicks) {
                    4 -> Toast.makeText(requireContext(), R.string.settings_joke_1, Toast.LENGTH_SHORT).show()
                    7 -> Toast.makeText(requireContext(), R.string.settings_joke_2, Toast.LENGTH_SHORT).show()
                    10 -> Toast.makeText(requireContext(), R.string.settings_joke_3, Toast.LENGTH_LONG).show()
                    15 -> Toast.makeText(requireContext(), R.string.settings_joke_4, Toast.LENGTH_LONG).show()
                    20 -> Toast.makeText(requireContext(), R.string.settings_joke_5, Toast.LENGTH_LONG).show()
                }
            }
            Setting.FEEDBACK -> {
                val intent = Intent(Intent.ACTION_SENDTO) // it's not ACTION_SEND
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.settings_feedback_subject, BuildConfig.VERSION_NAME))
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.settings_feedback_body))
                intent.data = Uri.parse("mailto:yaroslavlancelot@gmail.com")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    private fun initProviders(container: View) {
        val providers = appSettings.getProviders()
        if (providers.contains(ProviderType.CODEGUIDA)) container.codeguida.isChecked = true
        if (providers.contains(ProviderType.DOU)) container.dou.isChecked = true
        if (providers.contains(ProviderType.PINGVIN)) container.pingvin.isChecked = true
        if (providers.contains(ProviderType.TOKAR)) container.tokar.isChecked = true
        container.codeguida.setOnCheckedChangeListener { _, checked -> providerClick(ProviderType.CODEGUIDA, checked) }
        container.dou.setOnCheckedChangeListener { _, checked -> providerClick(ProviderType.DOU, checked) }
        container.pingvin.setOnCheckedChangeListener { _, checked -> providerClick(ProviderType.PINGVIN, checked) }
        container.tokar.setOnCheckedChangeListener { _, checked -> providerClick(ProviderType.TOKAR, checked) }
    }

    private fun initHistory(radioGroup: RadioGroup) {
        radioGroup.check(
            when (appSettings.historyStorage) {
                HistoryStorage.FEW_MONTHS -> R.id.a_few_months_button
                HistoryStorage.HALF_A_YEAR -> R.id.half_a_year_button
                HistoryStorage.YEAR -> R.id.year_button
            }
        )
    }

    private fun providerClick(type: ProviderType, selected: Boolean) {
        val providers = appSettings.getProviders().toMutableSet()
        if (selected) providers.add(type)
        else providers.remove(type)
        appSettings.updateProviders(providers)
    }

    private fun onHistoryChanged(@Suppress("UNUSED_PARAMETER") group: RadioGroup, @IdRes checkedId: Int) {
        appSettings.historyStorage = when (checkedId) {
            R.id.a_few_months_button -> HistoryStorage.FEW_MONTHS
            R.id.half_a_year_button -> HistoryStorage.HALF_A_YEAR
            R.id.year_button -> HistoryStorage.YEAR
            else -> appSettings.historyStorage
        }
    }
}