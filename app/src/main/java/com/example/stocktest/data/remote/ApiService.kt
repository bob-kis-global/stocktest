package com.example.stocktest.data.remote

import com.example.stocktest.data.model.LoginBody
import com.example.stocktest.data.model.LoginResult
import com.example.stocktest.data.model.Ticker
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("rest/api/v1/login")
    suspend fun doLogin(@Body body: LoginBody) : Response<LoginResult>

    @GET("rest/api/v2/market/symbol/latest")
    suspend fun getLatestSymbols(@Query("symbolList") symbolList: List<String>) : Response<List<Ticker>>

}