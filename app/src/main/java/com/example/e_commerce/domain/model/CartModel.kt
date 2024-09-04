package com.example.e_commerce.domain.model

import com.google.firebase.firestore.DocumentId

data class CartModel(
    @DocumentId val cartId : String = "",
    val userId: String ="",
    val items: List<CartItemModel> = emptyList(),
)

data class CartItemModel(
    val productId: Int,
    val price: Double = 0.0,
    val quantity: Int = 0,
)