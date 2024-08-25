package com.example.e_commerce.data.repo

import com.example.e_commerce.common.Resources
import com.example.e_commerce.data.remote.ApiService
import com.example.e_commerce.data.remote.dto.toModel
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.domain.repo.CommerceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CommerceRepositoryImpl @Inject constructor(private val api: ApiService) : CommerceRepository {
    override suspend fun getProducts(): Flow<Resources<List<ProductModel>>> {
        return flow {
            try {
                emit(Resources.Loading())
                val productsList = api.getAllProducts().map { it.toModel() }
                emit(Resources.Success(data = productsList))
            } catch (e: HttpException) {
                emit(Resources.Error(message = e.message() ?: "Please check your connection!"))
            } catch (e: Exception) {
                emit(Resources.Error(message = e.message ?: "Unexpected Error occurred"))
            }
        }
    }

    override suspend fun getProduct(id: Int): Flow<Resources<ProductModel>> {
        return flow {
            emit(Resources.Loading())
            try {
                val product = api.getProduct(id).toModel()
                emit(Resources.Success(data = product))
            } catch (e: HttpException) {
                emit(Resources.Error(message = e.message() ?: "Please check your connection!"))
            } catch (e: Exception) {
                emit(Resources.Error(message = e.message ?: "Unexpected Error occurred"))
            }
        }
    }

    override suspend fun getCategories(): List<String> {
        return api.getCategories()
    }

    override suspend fun getProductsByCategory(category: String): Flow<Resources<List<ProductModel>>> {
        return flow {
            emit(Resources.Loading())
            try {
                val productsList = api.getProductsByCategory(category).map { it.toModel() }
                emit(Resources.Success(data = productsList))
            } catch (e: HttpException) {
                emit(Resources.Error(message = e.message() ?: "Please check your connection!"))
            } catch (e: Exception) {
                emit(Resources.Error(message = e.message ?: "Unexpected Error occurred"))
            }
        }
    }

    override suspend fun getAllCarts(): Flow<Resources<List<CartModel>>> {
        return flow {
            emit(Resources.Loading())
            try {
                val cartsList = api.getAllCarts().map { it.toModel() }
                emit(Resources.Success(data = cartsList))
            } catch (e: HttpException) {
                emit(Resources.Error(message = e.message() ?: "Please check your connection!"))
            } catch (e: Exception) {
                emit(Resources.Error(message = e.message ?: "Unexpected Error occurred"))
            }
        }
    }

    override suspend fun getCart(id: Int): Flow<Resources<CartModel>> {
        return flow {
            emit(Resources.Loading())
            try {
                val cart = api.getCart(id).toModel()
                emit(Resources.Success(data = cart))
            } catch (e: HttpException) {
                emit(Resources.Error(message = e.message() ?: "Please check your connection!"))
            } catch (e: Exception) {
                emit(Resources.Error(message = e.message ?: "Unexpected Error occurred"))
            }
        }
    }

}