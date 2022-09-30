package com.example.stocktest.data

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.stocktest.data.local.AppDao
import com.example.stocktest.data.local.MarketData
import com.example.stocktest.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.net.URL

@HiltWorker
class MarketDataWorker @AssistedInject constructor(@Assisted private val context: Context,
                                                   @Assisted param: WorkerParameters,
                                                   val appDao: AppDao) : Worker(context, param) {
    override fun doWork(): Result {
        val url = inputData.getString(KEY_URL) ?: return Result.failure()
        val filename = inputData.getString(KEY_FILENAME) ?: return Result.failure()
        Timber.tag(TAG).d("MarketDataWorker doWork(0) : $url, $filename")

        try {
            FileUtil.downloadFile(context, URL(url), filename)
        } catch (e: Exception) {
            Timber.tag(TAG).d("MarketDataWorker doWork(1) : $e")
            return Result.failure()
        }

        Timber.tag(TAG).d("MarketDataWorker doWork(2) : success download")

        val data = FileUtil.readFile(context, filename)

        Timber.tag(TAG).d("MarketDataWorker doWork(3) : success read(${data.length})")

        val listMarketDataType = object : TypeToken<List<MarketData>>() {}.type
        var marketDataList: List<MarketData> = Gson().fromJson(data, listMarketDataType)

        Timber.tag(TAG).d("MarketDataWorker doWork(4) : success (${marketDataList.size})")

        appDao.deleteAll()
        appDao.insertAll(marketDataList)

        Timber.tag(TAG).d("MarketDataWorker doWork(5) : success (${appDao.getAllMarketDatas().size})")

        return Result.success()
    }

     companion object {
         const val TAG = "DownloadWorker-bob"
         const val KEY_URL = "KEY_URL"
         const val KEY_FILENAME = "KEY_FILENAME"
     }
}