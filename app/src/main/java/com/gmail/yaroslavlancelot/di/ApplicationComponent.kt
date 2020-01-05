package com.gmail.yaroslavlancelot.di

import android.content.Context
import com.gmail.yaroslavlancelot.TechNewsApplication
import com.gmail.yaroslavlancelot.di.viewmodel.ViewModelModule
import com.gmail.yaroslavlancelot.network.articles.NetworkModule
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
        ScreensModule::class
    ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<TechNewsApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}