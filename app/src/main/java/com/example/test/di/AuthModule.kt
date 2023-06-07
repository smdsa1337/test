package com.example.test.di

import com.example.test.repositories.SignInRepository
import com.example.test.service.SignInService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    @Singleton
    @Named("signInService")
    fun provideAuthService(@Named("retrofit") retrofit: Retrofit): SignInService =
        retrofit.create(SignInService::class.java)


    @Provides
    @Singleton
    @Named("signInRepository")
    fun provideDataRepository(@Named("signInService") service: SignInService): SignInRepository =
        SignInRepository(service)

}