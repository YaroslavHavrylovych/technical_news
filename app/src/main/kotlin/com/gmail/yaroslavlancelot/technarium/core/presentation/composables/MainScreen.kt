package com.gmail.yaroslavlancelot.technarium.core.presentation.composables

import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gmail.yaroslavlancelot.technarium.R
import com.gmail.yaroslavlancelot.technarium.core.navigation.Navigation
import com.gmail.yaroslavlancelot.technarium.core.navigation.Screen
import com.gmail.yaroslavlancelot.technarium.core.presentation.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    Surface(color = MaterialTheme.colors.background) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val originalValue = Screen.Articles
        val currentScreen = viewModel.currentScreen.observeAsState(originalValue)
        ModalDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                Drawer(
                    onDestinationClicked = { screen ->
                        scope.launch {
                            drawerState.close()
                            viewModel.openScreen(screen)
                        }
                    }
                )
            }
        ) {
            Scaffold(topBar = {
                TopBar(
                    currentScreen = currentScreen,
                    buttonIcon = ImageVector.vectorResource(id = R.drawable.ic_logo_white)
                ) {
                    scope.launch {
                        drawerState.open()
                    }
                }
            }) {
                Navigation(originalValue, currentScreen)
            }
        }
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}