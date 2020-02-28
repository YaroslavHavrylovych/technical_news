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
import androidx.lifecycle.MutableLiveData
import com.gmail.yaroslavlancelot.technarium.data.ProviderType
import com.gmail.yaroslavlancelot.technarium.data.local.HistoryReservable
import timber.log.Timber
import java.util.*


//TODO  split to multiple interfaces
class AppSettings(private val prefs: SharedPreferences) : HistoryReservable {
    private val historyKey = "tn_history_key"
    private val providersKey = "tn_providers_key"
    private val privacyAppliedKey = "tn_privacy_applied_key"
    private val privacyAppliedDateKey = "tn_privacy_applied_date_key"
    private val analyticsEnabledStatusKey = "tn_analytics_enabled_key"
    private val defaultAnalyticsStatus = false
    private var privacyPolicyDefaultValue = true
    val analyticsObserver = MutableLiveData<Boolean>(analyticsEnabled)

    var analyticsEnabled: Boolean
        get() = getAnalytics()
        set(value) {
            prefs.edit().putBoolean(analyticsEnabledStatusKey, value).apply()
            analyticsObserver.postValue(value)
        }

    var historyStorage: HistoryStorage = getHistory()
        get() {
            return getHistory()
        }
        set(value) {
            prefs.edit().putString(historyKey, value.value).apply()
            field = value
        }

    val privacyApplied: Boolean
        get() {
            return prefs.getBoolean(privacyAppliedKey, privacyPolicyDefaultValue)
        }

    fun applyPrivacy() {
        prefs.edit()
            .putLong(privacyAppliedDateKey, Date().time)
            .putBoolean(privacyAppliedKey, true)
            .apply()
    }

    fun getProviders(): Set<ProviderType> =
        HashSet<ProviderType>(
            prefs.getString(providersKey, ProviderType.values().joinToString(",") { it.providerName })!!
                .ifEmpty { "" }
                .split(",")
                .filter { it.isNotEmpty() }
                .map { ProviderType.fromString(it) })

    fun updateProviders(providers: Set<ProviderType>) =
        prefs.edit().putString(providersKey, providers.map { it.providerName }.joinToString(",")).apply()

    override fun oldestHistory(): Date {
        val period = when (getHistory()) {
            HistoryStorage.FEW_MONTHS -> 3
            HistoryStorage.HALF_A_YEAR -> 6
            HistoryStorage.YEAR -> 12
        }
        val c = Calendar.getInstance()
        c.add(Calendar.MONTH, -period)
        return c.time
    }

    private fun getHistory() = HistoryStorage.parse(prefs.getString(historyKey, HistoryStorage.default().value) as String)

    private fun getAnalytics() = prefs.getBoolean(analyticsEnabledStatusKey, defaultAnalyticsStatus)
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