package com.example.stocktest.domain

import com.example.stocktest.data.Result
import com.example.stocktest.data.model.LoginBody
import com.example.stocktest.data.model.LoginResult
import com.example.stocktest.data.model.Ticker
import kotlinx.coroutines.flow.Flow

interface IRemoteRepository {
    suspend fun doLogin(loginBody: LoginBody) : Flow<Result<LoginResult>?>
    suspend fun getLatestSymbols(symbolList: List<String>) : Flow<Result<List<Ticker>>>
}