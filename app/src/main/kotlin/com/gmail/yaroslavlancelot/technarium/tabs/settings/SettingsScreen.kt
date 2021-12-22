package com.gmail.yaroslavlancelot.technarium.tabs.settings

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()) {
    Text(
        viewModel.displayedString,
        color = Color.Blue,
    )
}

@Composable
@Preview
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen()
    }
}