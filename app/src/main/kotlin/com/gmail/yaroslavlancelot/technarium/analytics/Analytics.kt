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

package com.gmail.yaroslavlancelot.technarium.analytics

import timber.log.Timber

interface Analytics {
    fun appStarted()

    fun updateAnalyticsStatus(enabled: Boolean)
}

class AnalyticsImpl : Analytics {
    override fun appStarted() = Timber.i("App started")

    override fun updateAnalyticsStatus(enabled: Boolean) {}
}
