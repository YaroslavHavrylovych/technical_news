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

package com.gmail.yaroslavlancelot.technarium.screens.splash

import androidx.navigation.findNavController
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.data.DataRepository
import com.gmail.yaroslavlancelot.technarium.data.ItemType
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseFragment
import com.gmail.yaroslavlancelot.technarium.settings.AppSettings
import com.gmail.yaroslavlancelot.technarium.utils.extensions.observe
import javax.inject.Inject

class SplashFragment : BaseFragment() {
    @Inject lateinit var dataRepository: DataRepository
    @Inject lateinit var appSettings: AppSettings
    override fun getLayoutId() = R.layout.lt_splash_fragment

    override fun onResume() {
        super.onResume()
        observe(dataRepository.loadingStatus(ItemType.ARTICLE), {
            if (it == DataRepository.LoadingStatus.ERROR || it == DataRepository.LoadingStatus.LOADED) {
                view?.findNavController()?.navigate(SplashFragmentDirections.actionSplashToArticles())
            }
        })
        dataRepository.refreshArticles(appSettings.getProviders())
        dataRepository.refreshNews(appSettings.getProviders())
        dataRepository.refreshEvents(appSettings.getProviders())
        dataRepository.refreshOpenings(appSettings.getProviders())
    }
}