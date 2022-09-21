package com.example.stocktest.domain

import com.example.stocktest.data.model.LoginBody
import com.example.stocktest.data.model.LoginResult
import retrofit2.Response

interface IRepository {
    suspend fun doLogin(loginBody: LoginBody) : Response<LoginResult>
}