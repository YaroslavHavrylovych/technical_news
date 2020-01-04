package com.gmail.yaroslavlancelot.screens

import com.gmail.yaroslavlancelot.screens.news.NewsListFragment
import com.gmail.yaroslavlancelot.screens.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScreensModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun splashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun newsFragment(): NewsListFragment
}
