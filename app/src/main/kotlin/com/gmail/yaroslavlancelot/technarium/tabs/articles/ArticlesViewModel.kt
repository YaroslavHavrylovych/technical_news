package com.gmail.yaroslavlancelot.technarium.tabs.articles

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class ArticlesViewModel : @HiltViewModel ViewModel() {
    val displayedString = "Hello Articles"
}