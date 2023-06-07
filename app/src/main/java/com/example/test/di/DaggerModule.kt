package com.example.test.di

import com.example.test.repositories.SignInRepository
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
class DaggerModule {

    @Provides
    @Named("BASE_URL")
    fun provideBaseURL() : String = "http://10.111.207.156:8080/"

    @Provides
    @Named("Client")
    fun client(token : String) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }.build()
    }

    @Provides
    @Singleton
    @Named("Retrofit")
    fun provideRetrofit(
        @Named("BASE_URL") BASE_URL : String,
        @Named("Client") client : OkHttpClient
    ) : Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("Service")
    fun provideService(@Named("Retrofit") retrofit : Retrofit) : ApiClient =
        retrofit.create(ApiClient::class.java)

    @Provides
    @Singleton
    @Named("addTaskRepository")
    fun provideSignInRepository(@Named("Service") service : ApiClient) : SignInRepository =
        SignInRepository(service)

}