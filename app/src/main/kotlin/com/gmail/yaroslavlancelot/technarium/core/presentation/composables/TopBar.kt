package com.gmail.yaroslavlancelot.technarium.core.presentation.composables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.vector.ImageVector
import com.gmail.yaroslavlancelot.technarium.core.navigation.Screen

@Composable
fun TopBar(currentScreen: State<Screen>, buttonIcon: ImageVector, onButtonClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = currentScreen.value.route
            )
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(buttonIcon, contentDescription = "")
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant
    )
}