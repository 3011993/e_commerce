package com.example.e_commerce.domain.service

import com.example.e_commerce.domain.model.CartModel
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val carts: Flow<List<CartModel>>
    fun addCart(cart: CartModel,onResult :(Boolean) -> Unit)
    fun updateCart(cart: CartModel,onResult: (Boolean) -> Unit)
    suspend fun addOrUpdateCart(cart: CartModel,onResult: (Boolean) -> Unit)
    fun removeFromCart(cart: CartModel,onResult: (Boolean) -> Unit)
    fun getInStockStatus(productId : String, callBack :(Boolean) -> Unit)
}