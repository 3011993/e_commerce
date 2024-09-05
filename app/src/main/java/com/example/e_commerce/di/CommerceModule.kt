package com.example.e_commerce.di

import com.example.e_commerce.data.db.CommerceDao
import com.example.e_commerce.data.db.CommerceDataBase
import com.example.e_commerce.data.remote.ApiService
import com.example.e_commerce.data.repo.CommerceRepositoryImpl
import com.example.e_commerce.data.service.AccountServiceImpl
import com.example.e_commerce.data.service.LogServiceImpl
import com.example.e_commerce.data.service.StorageServiceImpl
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CommerceModule {
    @Binds
    abstract fun provideCommerceRepository(impl: CommerceRepositoryImpl): CommerceRepository
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
    @Binds
    abstract fun provideLogService(impl: LogServiceImpl): LogService
}