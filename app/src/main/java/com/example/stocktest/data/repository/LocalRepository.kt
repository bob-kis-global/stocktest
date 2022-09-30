package com.example.stocktest.data.repository

import com.example.stocktest.data.local.AppDao
import com.example.stocktest.data.local.MarketData
import com.example.stocktest.domain.ILocalRepository
import javax.inject.Inject

class LocalRepository @Inject constructor(private val appDao: AppDao): ILocalRepository {
    override suspend fun selectMarketData(s: String): List<MarketData>? {
        return appDao.getMarketData(s)
    }

    override suspend fun insertMarketDataList(marketDataList: List<MarketData>) {
        appDao.insertAll(marketDataList)
    }
}