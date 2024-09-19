package com.example.e_commerce.data.remote

import com.example.e_commerce.common.Constants.GET_ALL_CATEGORIES
import com.example.e_commerce.common.Constants.GET_ALL_PRODUCTS
import com.example.e_commerce.common.Constants.GET_PRODUCTS_BY_CATEGORY
import com.example.e_commerce.common.Constants.GET_SINGLE_PRODUCT
import com.example.e_commerce.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(GET_ALL_PRODUCTS)
    suspend fun getAllProducts(): List<ProductDto>

    @GET(GET_SINGLE_PRODUCT)
    suspend fun getProduct(@Path("id") productId: Int): ProductDto

    @GET(GET_ALL_CATEGORIES)
    suspend fun getCategories(): List<String>

    @GET(GET_PRODUCTS_BY_CATEGORY)
    suspend fun getProductsByCategory(@Path("category") category: String): List<ProductDto>


}