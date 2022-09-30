package com.example.stocktest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MarketData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}