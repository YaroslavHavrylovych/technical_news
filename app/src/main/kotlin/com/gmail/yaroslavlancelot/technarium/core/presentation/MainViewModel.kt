package com.gmail.yaroslavlancelot.technarium.core.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.yaroslavlancelot.technarium.core.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel

class MainViewModel : @HiltViewModel ViewModel() {
    private val _currentScreen: MutableLiveData<Screen> = MutableLiveData(Screen.Articles)
    val currentScreen: LiveData<Screen> = _currentScreen

    fun openScreen(screen: Screen) {
        _currentScreen.postValue(screen)
    }
}