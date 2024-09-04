package com.example.e_commerce.domain.service

import com.example.e_commerce.domain.model.CartItemModel
import com.example.e_commerce.domain.model.CartModel
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val carts: Flow<List<CartModel>>
    suspend fun saveCart(cart: CartModel)
    suspend fun updateCart(cart: CartModel)
    suspend fun deleteCart(cartId: String)
    suspend fun checkIfCartExists(userId: String) : Boolean
    suspend fun addToCart(cartId: String, newItem: CartItemModel)
    suspend fun removeFromCart(cartId: String, productId: Int)
}