package com.example.e_commerce.domain.model

import com.google.firebase.firestore.DocumentId

data class CartModel(
    @DocumentId val cartId : String = "",
    val userId: String ="",
    val productId: Int=0,
    val price: Double = 0.0,
    val originalPrice : Double = 0.0,
    val quantity: Int = 0,
    val image : String = "",
    val title : String = ""
)
