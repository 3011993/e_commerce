package com.example.e_commerce.di

import com.example.e_commerce.data.remote.ApiService
import com.example.e_commerce.data.repo.CommerceRepositoryImpl
import com.example.e_commerce.domain.repo.CommerceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CommerceModule {
    @Provides
    @Singleton
    fun provideCommerceRepository(apiService: ApiService): CommerceRepository {
        return CommerceRepositoryImpl(apiService)
    }
}