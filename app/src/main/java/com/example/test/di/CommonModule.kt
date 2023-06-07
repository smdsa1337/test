package com.example.test.di

import android.content.SharedPreferences
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    @Named("BASE_URL")
    fun provideBaseURL(): String = "https://test-api.redro.ru/api/"


    @Provides
    @Singleton
    @Named("retrofit")
    fun provideRetrofit(
        @Named("BASE_URL") BASE_URL: String,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Named("Client")
    fun client(@Named("sharedPreferencesToken") token : String) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest : Request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }.build()
    }

    @Provides
    @Singleton
    @Named("retrofit2")
    fun provideRetrofit2(
        @Named("BASE_URL") BASE_URL: String,
        @Named("Client") client : OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


}