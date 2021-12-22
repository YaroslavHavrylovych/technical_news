package com.gmail.yaroslavlancelot.technarium.tabs.events

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class EventsViewModel : @HiltViewModel ViewModel() {
    val displayedString = "Hello Events"
}