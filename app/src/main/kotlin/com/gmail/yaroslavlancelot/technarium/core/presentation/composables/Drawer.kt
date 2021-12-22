package com.gmail.yaroslavlancelot.technarium.core.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.core.navigation.Screen

private val screens = listOf(
    Screen.Articles, Screen.News, Screen.Events,
    Screen.Settings, Screen.Jobs, Screen.Selected
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (screen: Screen) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo_white),
            contentDescription = "App icon"
        )
        for (screen in screens) {
            Spacer(Modifier.height(24.dp))
            Text(
                text = screen.route,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.clickable {
                    onDestinationClicked(screen)
                }
            )
        }
    }
}