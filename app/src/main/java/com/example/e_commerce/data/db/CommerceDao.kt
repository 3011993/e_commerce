package com.example.e_commerce.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommerceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(vararg product: ProductEntity)

    @Query("SELECT * FROM PRODUCTENTITY")
    fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE category=:category")
    fun getProductsByCategory(category: String) : List<ProductEntity>
    @Query("SELECT * FROM PRODUCTENTITY WHERE isFavorite= 1")
    fun getFavoriteProducts() : List<ProductEntity>
    @Query("UPDATE PRODUCTENTITY SET isFavorite=:isFavourite WHERE id =:productId")
    fun updateFavourites(productId : Int,isFavourite : Boolean)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(vararg category: CategoriesEntity)

    @Query("SELECT * FROM CategoriesEntity")
    fun getAllCategories(): List<CategoriesEntity>

}