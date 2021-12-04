package com.gmail.yaroslavlancelot.technarium.core.navigation

sealed class Screen(route: String) {
    val route = route + "_screen"

    object News : Screen("news")
}
