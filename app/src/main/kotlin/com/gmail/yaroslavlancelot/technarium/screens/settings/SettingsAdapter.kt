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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.yaroslavlancelot.technarium.BuildConfig
import com.gmail.yaroslavlancelot.technarium.R
import kotlinx.android.synthetic.main.lt_settings_item.view.selection_area_view
import kotlinx.android.synthetic.main.lt_settings_item.view.subtitle
import kotlinx.android.synthetic.main.lt_settings_item.view.title

class SettingsAdapter(
    private val settings: List<Setting>,
    private var itemClickListener: (setting: Setting) -> Unit
) : RecyclerView.Adapter<SettingsAdapter.SettingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder =
        SettingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.lt_settings_item, parent, false))

    override fun getItemCount() = settings.count()

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        val setting = settings[position]
        holder.title.setText(titleProvider(setting))
        if (setting == Setting.VERSION) holder.subTitle.text = BuildConfig.VERSION_NAME
        else holder.subTitle.setText(subtitleProvider(setting))
        holder.clickArea.setOnClickListener { itemClickListener(setting) }
    }

    class SettingViewHolder(settingView: View) : RecyclerView.ViewHolder(settingView) {
        val title: TextView = itemView.title
        val subTitle: TextView = itemView.subtitle
        val clickArea: View = itemView.selection_area_view
    }

    companion object {
        fun titleProvider(setting: Setting) =
            when (setting) {
                Setting.HISTORY -> R.string.settings_history_title
                Setting.DATA_PROVIDERS -> R.string.settings_provider_title
                Setting.VERSION -> R.string.settings_version_title
                Setting.FEEDBACK -> R.string.settings_feedback_title
            }

        fun subtitleProvider(setting: Setting) =
            when (setting) {
                Setting.HISTORY -> R.string.settings_history_subtitle
                Setting.DATA_PROVIDERS -> R.string.settings_provider_subtitle
                Setting.VERSION -> R.string.settings_version_subtitle
                Setting.FEEDBACK -> R.string.settings_feedback_subtitle
            }
    }
}

enum class Setting {
    HISTORY, DATA_PROVIDERS, FEEDBACK, VERSION
}