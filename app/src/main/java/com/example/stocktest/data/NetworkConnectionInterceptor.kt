package com.example.stocktest.data

import android.content.Context
import com.example.stocktest.data.exception.NetworkConnectionLostException
import com.example.stocktest.utils.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        with (chain) {
            if (!NetworkUtil.isNetworkAvailable(context)) {
                // TODO 네트워크 예외처리
                throw NetworkConnectionLostException()
            }

            val newRequest = request().newBuilder().build()
            proceed(newRequest)
        }
}