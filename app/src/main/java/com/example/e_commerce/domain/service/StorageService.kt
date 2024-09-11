package com.example.e_commerce.domain.service

import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.presentation.Cart
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val carts: Flow<List<CartModel>>
    suspend fun saveCart(cart: CartModel)
    suspend fun updateCart(cart: CartModel)
    suspend fun deleteCart(cartId: String)
    suspend fun checkIfCartExists(userId: String): Boolean
    suspend fun addToCart(cart: CartModel)
    suspend fun removeFromCart(cart: CartModel)
}