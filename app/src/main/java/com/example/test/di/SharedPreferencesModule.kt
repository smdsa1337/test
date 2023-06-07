package com.example.test.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {
    @Provides
    @Named("sharedPreferencesToken")
    fun provideTokenSharedPreferences(application: Application): String {
        return application.getSharedPreferences("token", Context.MODE_PRIVATE).getString("accessToken", "").toString()
    }
}