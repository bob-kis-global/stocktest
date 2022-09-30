package com.example.stocktest

import androidx.hilt.work.HiltWorkerFactory
import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.stocktest.data.MarketDataWorker
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : MultiDexApplication(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        createMarketData()
    }

    private fun createMarketData() {
        Timber.tag(TAG).d("downloadMarketData()")
        val data = Data.Builder()
            .putString(MarketDataWorker.KEY_URL, MARKET_DATA_URL)
            .putString(MarketDataWorker.KEY_FILENAME, MARKET_DATA_FILENAME)
            .build()

        val workMarketData = OneTimeWorkRequestBuilder<MarketDataWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workMarketData)
    }

    companion object {
        const val TAG = "App-bob"
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
    }
}