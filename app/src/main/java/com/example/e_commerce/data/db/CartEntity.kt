package com.example.e_commerce.data.db

data class CartEntity(
    val userId : String,
    val products : List<ProductEntity>
)