/**
 * Copyright (C) 2020 Yaroslav Havrylovych Open Source Project
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

package com.gmail.yaroslavlancelot.screens

import androidx.lifecycle.ViewModel
import com.gmail.yaroslavlancelot.di.viewmodel.ViewModelKey
import com.gmail.yaroslavlancelot.screens.news.NewsListFragment
import com.gmail.yaroslavlancelot.screens.news.NewsViewModel
import com.gmail.yaroslavlancelot.screens.splash.SplashFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ScreensModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun splashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun newsFragment(): NewsListFragment

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindsNewsViewModel(newsViewModel: NewsViewModel): ViewModel
}
