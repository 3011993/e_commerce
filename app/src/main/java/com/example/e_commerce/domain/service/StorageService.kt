package com.example.e_commerce.domain.service

import com.example.e_commerce.domain.model.CartModel
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val carts: Flow<List<CartModel>>
    fun addCart(cart: CartModel)
    fun updateCart(cart: CartModel)
    suspend fun addOrUpdateCart(cart: CartModel)
    fun removeFromCart(cart: CartModel)
}