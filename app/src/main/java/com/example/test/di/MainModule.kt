package com.example.test.di

import com.example.test.repositories.MainRepository
import com.example.test.service.ProductsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    @Named("mainService")
    fun provideAuthService(@Named("retrofit2") retrofit: Retrofit): ProductsService =
        retrofit.create(ProductsService::class.java)

    @Provides
    @Singleton
    @Named("mainRepository")
    fun provideDataRepository(@Named("mainService") service: ProductsService): MainRepository =
        MainRepository(service)
}