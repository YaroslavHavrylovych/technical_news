package com.gmail.yaroslavlancelot.technarium.news

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class NewsViewModel : @HiltViewModel ViewModel() {
    val displayedString = "Hello News"
}