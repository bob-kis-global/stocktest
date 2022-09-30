package com.example.stocktest.data.model

import com.example.stocktest.data.local.MarketData
import com.google.gson.annotations.SerializedName

data class LoginResult (
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("refreshToken") val refreshToken : String,
    @SerializedName("userInfo") val userInfo : UserInfo?,
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


data class Ticker(
    @SerializedName("a") val a : Double,
    @SerializedName("c") val c : Int,
    @SerializedName("h") val h : Int,
    @SerializedName("l") val l : Int,
    @SerializedName("o") val o : Int,
    @SerializedName("s") val s : String,
    @SerializedName("t") val t : String?,
    @SerializedName("ba") val ba : Int?,
    @SerializedName("bb") val bb : List<CPV>,
    @SerializedName("bo") val bo : List<CPV>,
    @SerializedName("ch") val ch : Int,
    @SerializedName("fr") val fr : FR,
    @SerializedName("ic") val ic : List<IC>?,
    @SerializedName("mb") val mb : String?,
    @SerializedName("mv") val mv : Int,
    @SerializedName("ra") val ra : Double,
    @SerializedName("ss") val ss : String?,
    @SerializedName("tb") val tb : Int?,
    @SerializedName("to") val to : Int?,
    @SerializedName("va") val va : Double,
    @SerializedName("vo") val vo : Int,
    @SerializedName("pva") val pva : Int,
    @SerializedName("pvo") val pvo : Int,
    @SerializedName("tc") val tc : Int?,
    @SerializedName("utc") val tuc : Int?,
    @SerializedName("marketData") var marketData : MarketData?,
) {
    data class CPV(
        @SerializedName("c") val c : Int,
        @SerializedName("p") val p : Int,
        @SerializedName("v") val v : Int,
    )
    data class FR(
        @SerializedName("bv") val bv : Int,
        @SerializedName("cr") val cr : Double,
        @SerializedName("sv") val sv : Int,
        @SerializedName("tr") val tr : Double,
    )
    data class IC(
        @SerializedName("ce") val ce : Int,
        @SerializedName("dw") val dw : Int,
        @SerializedName("fl") val fl : Int,
        @SerializedName("uc") val uc : Int,
        @SerializedName("up") val up : Int,
    )
}