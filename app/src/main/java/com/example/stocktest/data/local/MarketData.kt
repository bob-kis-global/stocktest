package com.example.stocktest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MarketData")
data class MarketData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val s: String,
    val m: String,
    val n1: String,
    val n2: String,
    val t: String,
    val i: Boolean?,
    val b: String?,
    val bs: String?,
)