package com.example.stocktest.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.stocktest.*
import com.example.stocktest.data.NetworkConnectionInterceptor
import com.example.stocktest.data.local.AppDatabase
import com.example.stocktest.data.remote.ApiService
import com.example.stocktest.data.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    fun provideBaseUrl() = ServerHosts.withPhase(BuildConfig.PHASE).url

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext appContext: Context) : OkHttpClient {

        val httpClient = OkHttpClient.Builder().apply {
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        }

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        httpClient
            .addInterceptor(NetworkConnectionInterceptor(appContext))
            .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build()
            it.proceed(request)
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

    @Singleton
    @Provides
    fun provideRepository(apiService:ApiService)= RemoteRepository(apiService)

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_preference", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_db"
    ).build()

    @Singleton
    @Provides
    fun provideAppDao(db: AppDatabase) = db.appDao()
}
