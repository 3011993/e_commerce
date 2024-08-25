package com.example.e_commerce.domain.model

data class CartModel(
    val date: String,
    val id: Int,
    val productCarts: List<ProductCartModel>,
    val userId: Int
)
data class ProductCartModel(
    val productId: Int,
    val quantity: Int
)