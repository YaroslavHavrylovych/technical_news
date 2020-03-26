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

package com.gmail.yaroslavlancelot.technarium

import com.gmail.yaroslavlancelot.technarium.analytics.Analytics
import com.gmail.yaroslavlancelot.technarium.di.DaggerApplicationComponent
import com.gmail.yaroslavlancelot.technarium.settings.AppSettings
import dagger.android.DaggerApplication
import timber.log.Timber

import com.gmail.yaroslavlancelot.technarium.utils.debug.ReleaseTree
import com.gmail.yaroslavlancelot.technarium.workers.NotificationWorker

import timber.log.Timber.DebugTree
import javax.inject.Inject

class TechNewsApplication : DaggerApplication() {
    @Inject lateinit var analytics: Analytics
    @Inject lateinit var appSettings: AppSettings

    override fun applicationInjector() = DaggerApplicationComponent.factory().create(applicationContext)

    override fun onCreate() {
        super.onCreate()
        analytics.appStarted()
        appSettings.analyticsObserver.observeForever { enabled -> analytics.updateAnalyticsStatus(enabled) }
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
        else Timber.plant(ReleaseTree())
        NotificationWorker.enqueueWithPeriod(applicationContext, NotificationWorker.Companion.NotificationPeriod.LUNCH)
    }

    companion object {
        const val tag = "nr_notification_worker_tag"
    }
}