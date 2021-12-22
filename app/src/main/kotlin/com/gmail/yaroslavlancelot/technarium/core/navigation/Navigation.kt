package com.gmail.yaroslavlancelot.technarium.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gmail.yaroslavlancelot.technarium.tabs.articles.ArticlesScreen
import com.gmail.yaroslavlancelot.technarium.tabs.events.EventsScreen
import com.gmail.yaroslavlancelot.technarium.tabs.jobs.JobsScreen
import com.gmail.yaroslavlancelot.technarium.tabs.news.NewsScreen
import com.gmail.yaroslavlancelot.technarium.tabs.selected.SelectedScreen
import com.gmail.yaroslavlancelot.technarium.tabs.settings.SettingsScreen

@Composable
fun Navigation(originalValue: Screen, currentScreen: State<Screen>) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = originalValue.route) {
        composable(route = Screen.News.route) {
            NewsScreen(hiltViewModel())
        }
        composable(route = Screen.Articles.route) {
            ArticlesScreen(hiltViewModel())
        }
        composable(route = Screen.Jobs.route) {
            JobsScreen(hiltViewModel())
        }
        composable(route = Screen.Events.route) {
            EventsScreen(hiltViewModel())
        }
        composable(route = Screen.Selected.route) {
            SelectedScreen(hiltViewModel())
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(hiltViewModel())
        }
    }
    navController.navigate(currentScreen.value.route)
}