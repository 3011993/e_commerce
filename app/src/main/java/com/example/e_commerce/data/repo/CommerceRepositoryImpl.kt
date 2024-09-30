package com.example.e_commerce.data.repo

import android.content.SharedPreferences
import android.util.Log
import com.example.e_commerce.common.Resources
import com.example.e_commerce.data.db.CategoriesEntity
import com.example.e_commerce.data.db.CommerceDao
import com.example.e_commerce.data.db.toModel
import com.example.e_commerce.data.remote.ApiService
import com.example.e_commerce.data.remote.dto.toDatabase
import com.example.e_commerce.data.remote.dto.toModel
import com.example.e_commerce.domain.model.CategoriesModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.domain.repo.CommerceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CommerceRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: CommerceDao,
    private val sharedPreferences: SharedPreferences,
) : CommerceRepository {
    override suspend fun getProducts(): Flow<Resources<List<ProductModel>>> {
        return flow {
            try {
                emit(Resources.Loading())
                val result = api.getAllProducts().toDatabase()
                dao.insertProducts(*result)
                val products = dao.getAllProducts().map { it.toModel() }
                emit(Resources.Success(data = products))
            } catch (e: HttpException) {
                val products = dao.getAllProducts().map { it.toModel() }
                emit(
                    Resources.Error(
                        message = e.message() ?: "Please check your connection!",
                        data = products
                    )
                )
            } catch (e: IOException) {
                val products = dao.getAllProducts().map { it.toModel() }
                emit(
                    Resources.Error(
                        message = e.message ?: "Unexpected Error occurred",
                        data = products
                    )
                )
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

    override suspend fun getCategories(): Flow<List<CategoriesModel>> {
        return flow {
            try {
                val categoriesEntity = api.getCategories().mapIndexed { index, category ->
                    CategoriesEntity(id = index + 1, category)
                }.toTypedArray()
                dao.insertCategories(*categoriesEntity)
                val cachedCategories = dao.getAllCategories().map { it.toModel() }
                emit(cachedCategories)
            } catch (e: Exception) {
                Log.i("repo", e.message ?: "Unexpected Error occurred")
            }
        }
    }

    override suspend fun getFavouriteProducts(): Flow<Resources<List<ProductModel>>> {
        return flow {
            try {
                emit(Resources.Loading())
                val products = dao.getFavoriteProducts().map { it.toModel() }
                emit(Resources.Success(data = products))
            } catch (e: HttpException) {
                val products = dao.getAllProducts().map { it.toModel() }
                emit(
                    Resources.Error(
                        message = e.message() ?: "Please check your connection!",
                        data = products
                    )
                )
            } catch (e: IOException) {
                val products = dao.getFavoriteProducts().map { it.toModel() }
                emit(
                    Resources.Error(
                        message = e.message ?: "Unexpected Error occurred",
                        data = products
                    )
                )
            }
        }
    }

    override suspend fun updateFavouriteStatus(productId: Int, isFavourite: Boolean) {
        dao.updateFavourites(productId,isFavourite)
    }

    override suspend fun getProductsByCategory(category: String): Flow<Resources<List<ProductModel>>> {
        return flow {
            emit(Resources.Loading())
            try {
                val result = api.getProductsByCategory(category).toDatabase()
                dao.insertProducts(*result)
                val productsList = dao.getProductsByCategory(category).map { it.toModel() }
                emit(Resources.Success(data = productsList))
            } catch (e: HttpException) {
                emit(Resources.Error(message = e.message ?: "Unexpected Error occurred"))
            } catch (e: IOException) {
                val productsList = dao.getProductsByCategory(category).map { it.toModel() }
                emit(
                    Resources.Error(
                        message = e.message ?: "Please check your connection!",
                        data = productsList
                    )
                )
            }
        }
    }

    override fun saveCartIdForProduct(productId: String, cartId: String) {
        val editor = sharedPreferences.edit()
        editor.putString(productId, cartId)
        editor.apply()
    }

    override fun getCartIdForProduct(productId: String): String? {
        return sharedPreferences.getString(productId, null)
    }

}