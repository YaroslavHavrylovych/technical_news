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
