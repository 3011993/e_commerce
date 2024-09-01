package com.example.e_commerce.di

import android.content.Context
import androidx.room.Room
import com.example.e_commerce.data.db.CommerceDao
import com.example.e_commerce.data.db.CommerceDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun getCommerceDatabase(@ApplicationContext context: Context): CommerceDataBase {
        return Room.databaseBuilder(
            context, CommerceDataBase::class.java, "commerce_database"
        ).build()
    }

    @Provides
    @Singleton
    fun getGameDao(database: CommerceDataBase): CommerceDao {
        return database.commerceDao
    }
}