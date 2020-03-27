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

package com.gmail.yaroslavlancelot.technarium.di

import android.content.Context
import com.gmail.yaroslavlancelot.technarium.TechNewsApplication
import com.gmail.yaroslavlancelot.technarium.analytics.AnalyticsModule
import com.gmail.yaroslavlancelot.technarium.data.DataModule
import com.gmail.yaroslavlancelot.technarium.data.local.LocalModule
import com.gmail.yaroslavlancelot.technarium.di.viewmodel.ViewModelModule
import com.gmail.yaroslavlancelot.technarium.data.network.NetworkModule
import com.gmail.yaroslavlancelot.technarium.screens.ScreensModule
import com.gmail.yaroslavlancelot.technarium.settings.SettingsModule
import com.gmail.yaroslavlancelot.technarium.notification.NotificationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        ScreensModule::class,
        DataModule::class,
        LocalModule::class,
        AnalyticsModule::class,
        SettingsModule::class,
        NotificationModule::class
    ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<TechNewsApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}