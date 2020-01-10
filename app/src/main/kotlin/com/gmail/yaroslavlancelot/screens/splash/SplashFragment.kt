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

package com.gmail.yaroslavlancelot.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.gmail.yaroslavlancelot.R
import com.gmail.yaroslavlancelot.screens.BaseFragment
import kotlinx.coroutines.*

class SplashFragment : BaseFragment() {
    private var splashTransitionJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lt_splash_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        splashTransitionJob = launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                view?.findNavController()?.navigate(
                    SplashFragmentDirections.actionSplashFragmentToNewsListFragment()
                )
            }
        }
    }

    override fun onStop() {
        splashTransitionJob?.cancel()
        super.onStop()
    }
}