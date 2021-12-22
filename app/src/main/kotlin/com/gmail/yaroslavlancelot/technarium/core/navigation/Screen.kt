package com.gmail.yaroslavlancelot.technarium.core.navigation

sealed class Screen(route: String) {
    val route = route + "_screen"

    object Articles : Screen("articles")
    object News : Screen("news")
    object Jobs : Screen("jobs")
    object Events : Screen("events")
    object Selected : Screen("selected")
    object Settings : Screen("settings")
}
