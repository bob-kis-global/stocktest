package com.example.stocktest.data.repository

import com.example.stocktest.data.model.LoginBody
import com.example.stocktest.data.model.LoginResult
import com.example.stocktest.data.model.Ticker
import com.example.stocktest.data.remote.ApiService
import com.example.stocktest.domain.IRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import com.example.stocktest.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteRepository(private val apiService: ApiService) : IRepository {
    override suspend fun doLogin(loginBody: LoginBody) : Flow<Result<LoginResult>> {
        return flow {
            emit(Result.loading(null))
            kotlinx.coroutines.delay(1000)
            emit(Result.success(LoginResult("","",null,0L,0L)))
//            val result = apiService.doLogin(loginBody)
//            if (result.isSuccessful) {
//                emit(Result.success(result.body()))
//            } else {
//                emit(Result.error(errorMessage(result), null))
//            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getLatestSymbols(symbolList: List<String>): Flow<Result<List<Ticker>>> {
        return flow {
            emit(Result.loading(null))
            val result = apiService.getLatestSymbols(symbolList)
            if (result.isSuccessful) {
                emit(Result.success(result.body()))
            } else {
                emit(Result.error(errorMessage(result), null))
            }
        }.flowOn(Dispatchers.IO)
    }
}

fun <T> errorMessage(response: Response<T>) : String {
    var errorMessage = "${response.code()}"
    if (response.body() != null) {
        errorMessage += ":\n${response.body().toString()}"
    }

    Timber.d(">>> \n${errorMessage}")

    return errorMessage
}