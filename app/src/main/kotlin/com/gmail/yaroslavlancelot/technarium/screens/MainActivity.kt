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

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.screens.base.BaseActivity
import com.gmail.yaroslavlancelot.technarium.screens.itemslist.openings.OpeningsViewModel
import com.gmail.yaroslavlancelot.technarium.settings.AppSettings
import com.gmail.yaroslavlancelot.technarium.utils.extensions.getReferenceColor
import com.gmail.yaroslavlancelot.technarium.utils.extensions.observe
import kotlinx.android.synthetic.main.lt_main_activity.drawer_layout

import kotlinx.android.synthetic.main.lt_main_activity.nav_view
import kotlinx.android.synthetic.main.lt_main_activity.toolbar
import kotlinx.android.synthetic.main.lt_nav_drawer_header.view.theme_switcher
import kotlinx.android.synthetic.main.lt_privacy_policy.view.privacy_policy_link
import javax.inject.Inject

class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var appSettings: AppSettings
    private val viewModel: MainViewModel by viewModels(factoryProducer = { viewModelFactory })
    private val openingsViewMode: OpeningsViewModel by viewModels(factoryProducer = { viewModelFactory })
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val finalDestinations = setOf(
        R.id.news_list_fragment, R.id.articles_list_fragment, R.id.openings_list_fragment,
        R.id.events_list_fragment, R.id.selected_list_fragment, R.id.settings_fragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(appSettings.lightTheme)
        setContentView(R.layout.lt_main_activity)
        observe(viewModel.consentGiven) { consentGiven ->
            requireNotNull(consentGiven)
            if (!consentGiven) showPrivacyPolicy()
        }
        observe(openingsViewMode.getFiltered()) { filtered ->
            val dest = navController.currentDestination
            if (filtered != null && dest != null) updateWindowDecorations(filtered, dest)
        }
        navController.addOnDestinationChangedListener(this)
        appBarConfiguration = AppBarConfiguration(finalDestinations, drawer_layout)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
        setSupportActionBar(toolbar)
    }

    override fun onBackPressed() {
        if (finalDestinations.contains(navController.currentDestination!!.id)) finish()
        else super.onBackPressed()
    }

    override fun onSupportNavigateUp() = navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        updateWindowDecorations(openingsViewMode.isFiltered(), destination)
    }

    override fun onDestroy() {
        navController.removeOnDestinationChangedListener(this)
        super.onDestroy()
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

    private fun updateWindowDecorations(filtered: Boolean, destination: NavDestination) {
        if (destination.label == "SplashFragment") {
            toolbar.visibility = View.GONE
            window.statusBarColor = getReferenceColor(R.attr.colorOnBackground)
            window.navigationBarColor = getReferenceColor(R.attr.colorOnBackground)
        } else {
            toolbar.visibility = View.VISIBLE
            val applyFilter = filtered && destination.id == R.id.openings_list_fragment
            val toolbarColor = getReferenceColor(if (applyFilter) R.attr.colorFiltered else R.attr.colorAccent)
            val statusBarColor = getReferenceColor(if (applyFilter) R.attr.colorFilteredDark else R.attr.colorPrimaryDark)
            val checkedColor = getReferenceColor(if (applyFilter) R.attr.colorFilteredDrawerText else R.attr.colorSelectedDrawerText)
            val textColor = getReferenceColor(R.attr.colorOnBackgroundText)
            val colorStates = ColorStateList(
                arrayOf(
                    arrayOf(android.R.attr.state_checked).toIntArray(),
                    arrayOf(-android.R.attr.state_checked).toIntArray()
                ), arrayOf(checkedColor, textColor).toIntArray()
            )
            nav_view.itemIconTintList = colorStates
            nav_view.itemTextColor = colorStates
            nav_view.setBackgroundColor(getReferenceColor(R.attr.colorOnBackground))
            toolbar.setBackgroundColor(toolbarColor)
            window.statusBarColor = statusBarColor
            window.navigationBarColor = ContextCompat.getColor(this, R.color.bottomNavigationBar)
            val header = nav_view.getHeaderView(0)
            header.setBackgroundColor(statusBarColor)
            header.theme_switcher.setImageResource(if (appSettings.lightTheme) R.drawable.ic_moon else R.drawable.ic_sun)
            header.theme_switcher.setOnClickListener { setTheme(!appSettings.lightTheme); recreate() }
        }
    }

    private fun setTheme(light: Boolean) {
        if (light) setTheme(R.style.AppTheme)
        else setTheme(R.style.Grey_AppTheme)
        appSettings.lightTheme = light
    }
}