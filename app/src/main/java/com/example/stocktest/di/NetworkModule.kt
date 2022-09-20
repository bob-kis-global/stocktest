package com.example.stocktest.di

import com.example.stocktest.*
import com.example.stocktest.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = ServerHosts.withPhase(Phase.BETA).url

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {

        val httpClient = OkHttpClient.Builder().apply {
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        }

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideMainRepository(apiService:ApiService)= MainRepository(apiService)
}
