package com.example.stocktest.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

object NumberUtil {

    fun getNumberFormat(value: Long): String {
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(value)
    }

    fun compactDecimalFormat(number: Double): String {
        val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')

        val value = floor(log10(number)).toInt()
        val base = value / 3

        return if (value >= 3 && base < suffix.size) {
            DecimalFormat("#0.0").format(
                number / 10.0.pow((base * 3).toDouble())
            ) + suffix[base]
        } else {
            DecimalFormat("#,##0").format(number)
        }
    }
}