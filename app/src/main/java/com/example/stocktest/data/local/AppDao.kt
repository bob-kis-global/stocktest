package com.example.stocktest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {

    @Query("SELECT * FROM MarketData")
    fun getAllMarketDatas(): List<MarketData>

    @Query("SELECT * FROM MarketData WHERE s = :s")
    fun getMarketData(s: String): List<MarketData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(marketData: MarketData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(marketDataList: List<MarketData>)

    @Query("DELETE FROM MarketData")
    fun deleteAll()

}