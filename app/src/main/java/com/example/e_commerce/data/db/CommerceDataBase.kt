package com.example.e_commerce.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class CommerceDataBase : RoomDatabase(){
    abstract val commerceDao : CommerceDao
}