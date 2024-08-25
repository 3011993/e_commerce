package com.example.e_commerce.domain.repo

import com.example.e_commerce.common.Resources
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface CommerceRepository {
    suspend fun getProducts(): Flow<Resources<List<ProductModel>>>
    suspend fun getProduct(id: Int): Flow<Resources<ProductModel>>
    suspend fun getCategories() : List<String>
    suspend fun getProductsByCategory(category : String) : Flow<Resources<List<ProductModel>>>
    suspend fun getAllCarts() : Flow<Resources<List<CartModel>>>
    suspend fun getCart(id : Int) : Flow<Resources<CartModel>>
}