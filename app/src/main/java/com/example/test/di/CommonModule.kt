package com.example.test.di

import android.content.SharedPreferences
import com.example.test.okhttp.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
    @Named("authInterceptor")
    fun provideAuthInterceptor(
        @Named("sharedPreferencesToken") sharedPreferences: SharedPreferences
    ): AuthInterceptor {
        return AuthInterceptor(sharedPreferences)
    }

    @Provides
    @Named("okHttpClient")
    fun provideOkHttpClient(@Named("authInterceptor") interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    }


    @Provides
    @Singleton
    @Named("retrofit")
    fun provideRetrofit(
        @Named("BASE_URL") BASE_URL: String,
        @Named("okHttpClient") okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}