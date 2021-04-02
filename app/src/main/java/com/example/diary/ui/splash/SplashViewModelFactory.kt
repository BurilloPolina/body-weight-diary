package com.example.diary.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diary.model.prefs.Prefs

class SplashViewModelFactory(
    private val prefs: Prefs
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(prefs) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
