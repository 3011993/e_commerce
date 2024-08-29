package com.example.e_commerce.di

import com.example.e_commerce.data.service.AccountServiceImpl
import com.example.e_commerce.domain.service.AccountService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AccountServiceModule {

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideAccountService(auth: FirebaseAuth): AccountService {
        return AccountServiceImpl(auth)
    }
}