package com.example.stocktest.presentation.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocktest.WATCH_LIST
import com.example.stocktest.data.Result
import com.example.stocktest.data.model.Ticker
import com.example.stocktest.data.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val preferences: SharedPreferences
    ) : ViewModel() {

    private val _watchList = MutableLiveData<Result<List<Ticker>>>()
    val watchList : LiveData<Result<List<Ticker>>>
        get() = _watchList

    private val isRequested = AtomicBoolean(false)

    fun getLatestSymbols() {
        Timber.tag(TAG).d("getLatestSymbols(0) : ${isRequested.get()}")
        viewModelScope.launch {
            if (!isRequested.getAndSet(true)) {
                remoteRepository.getLatestSymbols(WATCH_LIST).collect {
                    Timber.tag(TAG).d("getLatestSymbols(2) : $it")
                    _watchList.value = it
                }
                isRequested.set(false)
                Timber.tag(TAG).d("getLatestSymbols(3)")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        const val TAG = "MainViewModel-bob"
    }
}