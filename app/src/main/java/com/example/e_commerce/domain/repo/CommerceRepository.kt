package com.example.e_commerce.domain.repo

import com.example.e_commerce.common.Resources
import com.example.e_commerce.domain.model.CategoriesModel
import com.example.e_commerce.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface CommerceRepository {
    suspend fun getProducts(): Flow<Resources<List<ProductModel>>>
    suspend fun getProduct(id: Int): Flow<Resources<ProductModel>>
    suspend fun getCategories(): Flow<List<CategoriesModel>>
    suspend fun getProductsByCategory(category: String): Flow<Resources<List<ProductModel>>>
    suspend fun getFavouriteProducts () : Flow<Resources<List<ProductModel>>>
    suspend fun addFavouriteFromHome(productId: Int, isFavourite : Boolean) : List<ProductModel>
    suspend fun removeFavouriteFromHome(productId: Int, isFavourite: Boolean) : List<ProductModel>
    suspend fun addFavouriteFromWishList(productId: Int, isFavourite : Boolean) : List<ProductModel>
    suspend fun removeFavouriteFromWishList(productId: Int, isFavourite: Boolean) : List<ProductModel>
    fun saveCartIdForProduct(productId : String,cartId : String)
    fun getCartIdForProduct(productId: String) : String?
}