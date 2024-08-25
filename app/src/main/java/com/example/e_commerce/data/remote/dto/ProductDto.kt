package com.example.e_commerce.data.remote.dto

import com.example.e_commerce.domain.model.ProductModel

data class ProductDto(
    val category: String?,
    val description: String?,
    val id: Int?,
    val image: String?,
    val price: String?,
    val title: String?,
)

fun ProductDto.toModel(): ProductModel =
    ProductModel(
        category ?: "",
        description ?: "",
        id ?: 0,
        image ?: "",
        price ?: "",
        title ?: ""
    )
