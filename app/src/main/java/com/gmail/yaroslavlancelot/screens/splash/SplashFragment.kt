package com.gmail.yaroslavlancelot.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.gmail.yaroslavlancelot.R
import com.gmail.yaroslavlancelot.screens.BaseFragment
import kotlinx.coroutines.*

class SplashFragment : BaseFragment() {
    private var splashTransitionJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_splash, container, false)
    }

    override fun onStart() {
        super.onStart()
        splashTransitionJob = launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                val safeView = view
                if (safeView != null)
                    Navigation.findNavController(safeView).navigate(
                        R.id.action_splashFragment_to_newsListFragment,
                        null, NavOptions.Builder()
                            .setPopUpTo(R.id.splashFragment, true).build()
                    )
            }
        }
    }

    override fun onStop() {
        splashTransitionJob?.cancel()
        super.onStop()
    }
}