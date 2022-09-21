package com.example.stocktest.data.model

import com.google.gson.annotations.SerializedName

data class LoginResult (
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("refreshToken") val refreshToken : String,
    @SerializedName("userInfo") val userInfo : UserInfo,
    @SerializedName("accExpiredTime") val accExpiredTime : Long,
    @SerializedName("refExpiredTime") val refExpiredTime : Long,
) {

    data class UserInfo(
        @SerializedName("username") val username : String,
        @SerializedName("id") val id : Long,
        @SerializedName("avatar") val avatar : String,
        @SerializedName("birthday") val birthday : String?,
        @SerializedName("email") val email : String?,
        @SerializedName("phoneCode") val phoneCode : String?,
        @SerializedName("phoneNumber") val phoneNumber : String?,
        @SerializedName("accounts") val accounts : List<Account>,
    ) {
        data class Account(
            @SerializedName("accountNumber") val accountNumber : String,
            @SerializedName("accountName") val accountName : String,
            @SerializedName("accountSubs") val AccountSubs : List<AccountSub>,
        ) {
            data class AccountSub(
                @SerializedName("type") val type : String,
                @SerializedName("bankAccounts") val bankAccounts : List<String>?,
            )
        }
    }
}