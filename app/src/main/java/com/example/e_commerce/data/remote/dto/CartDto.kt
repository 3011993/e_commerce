package com.example.e_commerce.data.remote.dto

import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.model.ProductCartModel

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

fun ProductCartDto.toModel() =
    ProductCartModel(
        productId ?: 0,
        quantity ?: 0
    )

fun CartDto.toModel(): CartModel {
    return CartModel(
        date = date ?: "",
        id = id ?: 0,
        productCarts = productCartDtos?.map { it!!.toModel() } ?: emptyList(),
        userId = userId ?: 0
    )
}