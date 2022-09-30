package com.example.stocktest.utils

import androidx.compose.ui.graphics.Color
import com.example.stocktest.data.model.Ticker

object ColorUtil {
    fun getMarketColor(ticker: Ticker): Color {
        return when(ticker.marketData?.m) {
            MarketColor.UPCOM.name -> MarketColor.UPCOM.color
            MarketColor.HOSE.name -> MarketColor.HOSE.color
            MarketColor.HNX.name -> MarketColor.HNX.color
            else -> Color.Black
        }
    }

    fun getStockColor(ticker: Ticker): Color {
        return if(ticker.o > ticker.c) {
            // decrease
            StockColor.DECREASE.color
        } else if(ticker.o < ticker.c) {
            // increase
            StockColor.INCREASE.color
        } else {
            StockColor.NEUTRAL.color
        }
    }

    enum class MarketColor(val color: Color) {
        UPCOM(Color(0xFFAFE1F9)),
        HOSE(Color(0xFF2E326F)),
        HNX(Color(0xFF3F7128));
    }

    enum class StockColor(val color: Color) {
        INCREASE(Color.Green), DECREASE(Color.Red), NEUTRAL(Color.Black)
    }
}