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

package com.gmail.yaroslavlancelot.di

import android.content.Context
import com.gmail.yaroslavlancelot.TechNewsApplication
import com.gmail.yaroslavlancelot.data.DataModule
import com.gmail.yaroslavlancelot.data.local.LocalModule
import com.gmail.yaroslavlancelot.di.viewmodel.ViewModelModule
import com.gmail.yaroslavlancelot.data.network.NetworkModule
import com.gmail.yaroslavlancelot.screens.ScreensModule
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
        NetworkModule::class,
        LocalModule::class
    ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<TechNewsApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}