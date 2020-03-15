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

package com.gmail.yaroslavlancelot.technarium.screens

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseActivity
import com.gmail.yaroslavlancelot.technarium.screens.main.MainViewModel
import com.gmail.yaroslavlancelot.technarium.utils.extensions.observe
import kotlinx.android.synthetic.main.lt_main_activity.drawer_layout

import kotlinx.android.synthetic.main.lt_main_activity.nav_view
import kotlinx.android.synthetic.main.lt_main_activity.toolbar
import kotlinx.android.synthetic.main.lt_privacy_policy.view.privacy_policy_link
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels(factoryProducer = { viewModelFactory })
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val finalDestinations = setOf(
        R.id.news_list_fragment, R.id.articles_list_fragment, R.id.openings_list_fragment,
        R.id.events_list_fragment, R.id.selected_list_fragment, R.id.settings_fragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lt_main_activity)
        appBarConfiguration = AppBarConfiguration(finalDestinations, drawer_layout)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
        setSupportActionBar(toolbar)
        observe(viewModel.consentGiven) { consentGiven ->
            requireNotNull(consentGiven)
            if (!consentGiven) showPrivacyPolicy()
        }
    }

    private fun showPrivacyPolicy() {
        val body = layoutInflater.inflate(R.layout.lt_privacy_policy, null)
        body.privacy_policy_link.movementMethod = LinkMovementMethod.getInstance()
        AlertDialog.Builder(this)
            .setTitle(R.string.privacy_policy_title)
            .setView(body)
            .setCancelable(false)
            .setPositiveButton(R.string.privacy_policy_agree) { _, _ -> viewModel.agreeToPrivacyPolicy() }
            .show()
    }

    override fun onBackPressed() {
        if (finalDestinations.contains(navController.currentDestination!!.id)) finish()
        else super.onBackPressed()
    }

    override fun onSupportNavigateUp() = navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}