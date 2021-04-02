package com.example.diary.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diary.model.entity.User
import com.example.diary.model.prefs.Prefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val prefs: Prefs
) : ViewModel() {

    private val _user = MutableLiveData<User?>()

    val user: LiveData<User?>
        get() = _user

    init {
        viewModelScope.launch {
            delay(2000)
            _user.value = prefs.user
        }
    }
}
