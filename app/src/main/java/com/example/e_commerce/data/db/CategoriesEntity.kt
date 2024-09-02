package com.example.e_commerce.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoriesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String,
)
