package com.example.stocktest.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserStateViewModel : ViewModel() {
    var isLoggedIn by mutableStateOf(false)
    var isBusy by mutableStateOf(false)
    var id by mutableStateOf("")

    fun signIn(id: String, pq: String) {
        viewModelScope.launch {
            isBusy = true
            delay(2000)
            isLoggedIn = true
            isBusy = false
        }
    }

    fun signOut() {
        viewModelScope.launch {
            isBusy = true
            delay(2000)
            isLoggedIn = false
            isBusy = false
        }
    }
}

val UserState = compositionLocalOf<UserStateViewModel> { error("User State Context Not Found!") }