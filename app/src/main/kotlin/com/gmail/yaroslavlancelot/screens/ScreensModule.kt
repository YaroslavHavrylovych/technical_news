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

package com.gmail.yaroslavlancelot.screens

import androidx.lifecycle.ViewModel
import com.gmail.yaroslavlancelot.di.ActivityScope
import com.gmail.yaroslavlancelot.di.viewmodel.ViewModelKey
import com.gmail.yaroslavlancelot.screens.preview.PreviewFragment
import com.gmail.yaroslavlancelot.screens.itemslist.article.ArticlesListFragment
import com.gmail.yaroslavlancelot.screens.itemslist.article.ArticlesViewModel
import com.gmail.yaroslavlancelot.screens.itemslist.news.NewsListFragment
import com.gmail.yaroslavlancelot.screens.itemslist.news.NewsViewModel
import com.gmail.yaroslavlancelot.screens.itemslist.openings.OpeningsListFragment
import com.gmail.yaroslavlancelot.screens.itemslist.openings.OpeningsViewModel
import com.gmail.yaroslavlancelot.screens.itemslist.openings.filter.FilterDialogFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ScreensModule {
    @ContributesAndroidInjector
    @ActivityScope
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun previewFragment(): PreviewFragment

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun articlesFragment(): ArticlesListFragment

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun newsFragment(): NewsListFragment

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun openingsFragment(): OpeningsListFragment

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun filterFragment(): FilterDialogFragment

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesViewModel::class)
    @Singleton
    abstract fun bindsArticlesViewModel(articlesViewModel: ArticlesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    @Singleton
    abstract fun bindsNewsViewModel(newsViewModel: NewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OpeningsViewModel::class)
    @Singleton
    abstract fun bindsOpeningsViewModel(openingsViewModel: OpeningsViewModel): ViewModel
}
