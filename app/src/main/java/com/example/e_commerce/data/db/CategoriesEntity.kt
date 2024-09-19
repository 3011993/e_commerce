package com.example.e_commerce.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.e_commerce.domain.model.CategoriesModel

@Entity
data class CategoriesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String,
)

fun CategoriesEntity.toModel() = CategoriesModel(
    id = id,
    category = category
)