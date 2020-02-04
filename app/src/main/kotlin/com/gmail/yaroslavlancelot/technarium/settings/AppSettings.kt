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

package com.gmail.yaroslavlancelot.technarium.settings

import android.content.SharedPreferences
import timber.log.Timber

class AppSettings(private val prefs: SharedPreferences) {
    private val historyKey = "tn_history_key"

    var historyStorage: HistoryStorage = getHistory()
        get() {
            return getHistory()
        }
        set(value) {
            prefs.edit().putString(historyKey, value.value).apply()
            field = value
        }

    private fun getHistory() = HistoryStorage.parse(prefs.getString(historyKey, HistoryStorage.default().value) as String)
}

enum class HistoryStorage(val value: String) {
    FEW_MONTHS("a_few_months"), HALF_A_YEAR("half_a_year"), YEAR("year");

    companion object {
        fun parse(value: String) =
            when (value) {
                FEW_MONTHS.value -> FEW_MONTHS
                HALF_A_YEAR.value -> HALF_A_YEAR
                YEAR.value -> YEAR
                else -> {
                    Timber.e("Unknown history storage")
                    default()
                }
            }

        fun default() = FEW_MONTHS
    }
}