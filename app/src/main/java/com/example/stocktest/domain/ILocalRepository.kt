package com.example.stocktest.domain

import com.example.stocktest.data.local.MarketData

interface ILocalRepository {
    suspend fun selectMarketData(s: String): List<MarketData>?
    suspend fun insertMarketDataList(marketDataList: List<MarketData>)
}