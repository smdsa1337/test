package com.example.test.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}