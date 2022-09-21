package com.example.stocktest.data.remote

import com.example.stocktest.data.model.LoginBody
import com.example.stocktest.data.model.LoginResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("rest/api/v1/login")
    suspend fun doLogin(@Body body: LoginBody) : Response<LoginResult>


}