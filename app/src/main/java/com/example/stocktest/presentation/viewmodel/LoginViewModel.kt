package com.example.stocktest.presentation.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocktest.ACCESS_TOKEN
import com.example.stocktest.BuildConfig
import com.example.stocktest.Phase
import com.example.stocktest.REFRESH_TOKEN
import com.example.stocktest.data.Result
import com.example.stocktest.data.model.LoginBody
import com.example.stocktest.data.model.LoginResult
import com.example.stocktest.data.repository.LocalRepository
import com.example.stocktest.data.repository.RemoteRepository
import com.example.stocktest.di.Modules
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val preferences: SharedPreferences
    ) : ViewModel() {

    private val _user = MutableLiveData<Result<LoginResult>>()
    val user : LiveData<Result<LoginResult>>
        get() = _user

    fun signIn(name: String, pw: String) {
        Timber.tag(TAG).d("signIn(0)")
        viewModelScope.launch {
            remoteRepository.doLogin(LoginBody(
                grantType = "password",
                clientId = loginInfo.clientId,
                clientSecret = loginInfo.clientSecret,
                username = name,
                password = pw
            )).collect {
                Timber.tag(TAG).d("signIn($name, $pw) = $it")
                _user.value = it
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {

        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    private fun storeLoginInfo(loginResult: LoginResult?) {
        loginResult?.let {
            preferences.edit()
                .putString(ACCESS_TOKEN, it.accessToken)
                .putString(REFRESH_TOKEN, it.refreshToken)
                .apply()
        }
    }

    private fun getLoginInfo() : Boolean {
        return !preferences.getString(ACCESS_TOKEN, "").isNullOrBlank()
                && !preferences.getString(REFRESH_TOKEN, "").isNullOrBlank()
    }


    companion object {
        const val TAG = "LoginViewModel-bob"
        val loginInfo = if(BuildConfig.PHASE == Phase.PRODUCTION.phaseName) {
            LoginBody(
                "",
                "kis-rest",
                "QzHZUA9TxvU2ANbHydihPf5GQdDI0tst05yM6Y19SsVMtfplx5",
                "",
                ""
            )
        } else {
            LoginBody(
                "",
                "kis-wts",
                "OTHopiy1uPz3Daos3Nlp92Gih6mD0OYo9E6J7p7UMccJhLtqf3CJ7a5J4JO4ah8v",
                "",
                ""
            )
        }
    }
}