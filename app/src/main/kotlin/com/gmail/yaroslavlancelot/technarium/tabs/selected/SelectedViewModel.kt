package com.gmail.yaroslavlancelot.technarium.tabs.selected

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class SelectedViewModel : @HiltViewModel ViewModel() {
    val displayedString = "Hello Selected"
}