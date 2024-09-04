package com.example.e_commerce.data.remote.dto

import com.example.e_commerce.domain.model.CartModel

data class CartDto(
    val date: String?,
    val id: Int?,
    val productCartDtos: List<ProductCartDto?>?,
    val userId: Int?,
)

data class ProductCartDto(
    val productId: Int?,
    val quantity: Int?,
)
