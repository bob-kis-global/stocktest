package com.example.stocktest.data.repository

import com.example.stocktest.data.model.LoginBody
import com.example.stocktest.data.model.LoginResult
import com.example.stocktest.data.remote.ApiService
import com.example.stocktest.domain.IRepository
import retrofit2.Response

class Repository(private val apiService: ApiService) : IRepository {
    override suspend fun doLogin(loginBody: LoginBody) : Response<LoginResult> {
        return apiService.doLogin(loginBody)
    }
}