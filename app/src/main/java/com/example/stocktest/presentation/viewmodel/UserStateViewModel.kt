package com.example.stocktest.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocktest.data.model.LoginBody
import com.example.stocktest.data.model.LoginResult
import com.example.stocktest.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserStateViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var isLoggedIn by mutableStateOf(false)
    var isBusy by mutableStateOf(false)

    private val _errorMessage by lazy { MutableLiveData<String>() }
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun signIn(name: String, pw: String) {
        viewModelScope.launch {
            isBusy = true
            val response = repository.doLogin(LoginBody(
                grantType = "password",
                clientId = "kis-wts",
                clientSecret = "OTHopiy1uPz3Daos3Nlp92Gih6mD0OYo9E6J7p7UMccJhLtqf3CJ7a5J4JO4ah8v",
                username = name,
                password = pw
            ))

            if (response.isSuccessful) {
                isLoggedIn = true
            } else {
                _errorMessage.postValue("Error ${response.code()} ${response.message()}")
            }

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

    override fun onCleared() {
        super.onCleared()
    }
}

val UserState = compositionLocalOf<UserStateViewModel> { error("User State Context Not Found!") }