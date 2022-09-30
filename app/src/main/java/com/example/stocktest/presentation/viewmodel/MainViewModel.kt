package com.example.stocktest.presentation.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocktest.WATCH_LIST
import com.example.stocktest.data.Result
import com.example.stocktest.data.local.MarketData
import com.example.stocktest.data.model.Ticker
import com.example.stocktest.data.repository.LocalRepository
import com.example.stocktest.data.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val preferences: SharedPreferences,
    private val localRepository: LocalRepository
    ) : ViewModel() {

    private val _watchList = MutableLiveData<Result<List<Ticker>>>()
    val watchList : LiveData<Result<List<Ticker>>>
        get() = _watchList

    private val isRequested = AtomicBoolean(false)

    fun getMainData() {
        Timber.tag(TAG).d("getLatestSymbols(0) : ${isRequested.get()}")
        viewModelScope.launch {
            if (!isRequested.getAndSet(true)) {
                _watchList.value = Result.loading(null)
                val marketDataList = getDescriptionList()
                remoteRepository.getLatestSymbols(WATCH_LIST).collect {
                    Timber.tag(TAG).d("getLatestSymbols(2) : $it")
                    it.data?.let { data ->
                        data.forEachIndexed { index, element ->
                            element.marketData = marketDataList[index]
                        }
                    }
                    _watchList.value = it
                }
                isRequested.set(false)
                Timber.tag(TAG).d("getLatestSymbols(3)")
            }

        }
    }

    private suspend fun getDescriptionList(): List<MarketData> {
        return withContext(Dispatchers.IO) {
            val descriptionList = mutableListOf<MarketData>()

            for (item in WATCH_LIST) {
                localRepository.selectMarketData(item)?.let {
                    descriptionList.add(it[0])
                }
            }

            descriptionList
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        const val TAG = "MainViewModel-bob"
    }
}