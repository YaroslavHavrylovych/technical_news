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

package com.gmail.yaroslavlancelot.technarium.helpers.di

import com.gmail.yaroslavlancelot.technarium.analytics.AnalyticsModule
import com.gmail.yaroslavlancelot.technarium.data.DataModule
import com.gmail.yaroslavlancelot.technarium.data.local.LocalModule
import com.gmail.yaroslavlancelot.technarium.di.ApplicationComponent
import com.gmail.yaroslavlancelot.technarium.di.viewmodel.ViewModelModule
import com.gmail.yaroslavlancelot.technarium.data.network.NetworkModule
import com.gmail.yaroslavlancelot.technarium.screens.ScreensModule
import com.gmail.yaroslavlancelot.technarium.settings.SettingsModule
import com.gmail.yaroslavlancelot.technarium.tests.network.NetworkModuleTest
import com.gmail.yaroslavlancelot.technarium.tests.screens.itemslist.main.MainViewModelTest
import com.gmail.yaroslavlancelot.technarium.notification.NotificationModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        ScreensModule::class,
        DataModule::class,
        NetworkModule::class,
        LocalModule::class,
        AnalyticsModule::class,
        SettingsModule::class,
        NotificationModule::class,
        TestAndroidModule::class
    ]
)
@Singleton
interface TestApplicationComponent : ApplicationComponent {
    fun inject(networkModuleTest: NetworkModuleTest)
    fun inject(mainViewModelTest: MainViewModelTest)
}