package com.example.stocktest

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

enum class Phase(val phaseName: String) {
    @SerializedName("beta")
    BETA("beta"),
    @SerializedName("production")
    PRODUCTION("production");

    companion object {
        /**
         * @suppress
         */
        fun fromName(value: String): Phase = Gson().fromJson(value, Phase::class.java)
    }
}