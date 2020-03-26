package com.gmail.yaroslavlancelot.technarium.tests.screens.itemslist.main

import com.gmail.yaroslavlancelot.technarium.helpers.di.DaggerTestApplicationComponent
import com.gmail.yaroslavlancelot.technarium.screens.MainViewModel
import com.gmail.yaroslavlancelot.technarium.settings.AppSettings
import com.gmail.yaroslavlancelot.technarium.tests.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import javax.inject.Inject

class MainViewModelTest : BaseTest() {
    @Inject @JvmField var appSettings: AppSettings? = null
    private lateinit var mainViewModel: MainViewModel

    override fun postSetup(dagger: DaggerTestApplicationComponent) {
        dagger.inject(this)
        mainViewModel = MainViewModel(appSettings!!)
    }

    @Test
    fun `verifying the user consent update within a model`() = runBlocking {
        var initialCheck = true
        mainViewModel.consentGiven.observeForever { status ->
            if (initialCheck) {
                assert(!status) { "Consent must be false, before the user agrees on it" }
                initialCheck = false
            } else {
                assert(status) { "Consent is not updated on agreement" }
            }
        }
        mainViewModel.agreeToPrivacyPolicy()
    }
}