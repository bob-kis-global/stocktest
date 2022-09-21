package com.example.stocktest.data.model

import com.google.gson.annotations.SerializedName

data class LoginBody(@SerializedName("grant_type") val grantType: String,
                     @SerializedName("client_id") val clientId: String,
                     @SerializedName("client_secret") val clientSecret: String,
                     @SerializedName("username") val username: String,
                     @SerializedName("password") val password: String)