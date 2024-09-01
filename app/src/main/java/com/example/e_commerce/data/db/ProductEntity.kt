package com.example.e_commerce.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.e_commerce.domain.model.ProductModel

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category: String,
    val description: String,
    val image: String,
    val price: String,
    val title: String,
)

fun ProductEntity.toModel() = ProductModel(
    id = id,
    category = category,
    description = description,
    image = image,
    price = price,
    title = title
)

fun ProductModel.fromModel() = ProductEntity(
    id = id,
    category = category,
    description = description,
    image = image,
    price = price,
    title = title
)