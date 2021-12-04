package com.gmail.yaroslavlancelot.technarium.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gmail.yaroslavlancelot.technarium.news.NewsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.News.route) {
        composable(route = Screen.News.route) {
            NewsScreen(hiltViewModel())
        }
    }
}