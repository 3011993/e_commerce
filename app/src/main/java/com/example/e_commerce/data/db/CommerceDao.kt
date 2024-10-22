package com.example.e_commerce.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface CommerceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(vararg product: ProductEntity)

    @Query("SELECT * FROM PRODUCTENTITY")
    fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE category=:category")
    fun getProductsByCategory(category: String): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(vararg category: CategoriesEntity)

    @Query("SELECT * FROM CategoriesEntity")
    fun getAllCategories(): List<CategoriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(favouriteEntity: FavouriteEntity)

    @Query("UPDATE PRODUCTENTITY SET isFavorite=:isFavourite WHERE id =:productId")
    fun updateFavourites(productId: Int, isFavourite: Boolean)

    @Query("DELETE FROM FavouriteEntity WHERE productId =:productId ")
    fun deleteFavourite(productId: Int)

    @Query("SELECT * FROM PRODUCTENTITY WHERE isFavorite= 1")
    fun getFavoriteProducts(): List<ProductEntity>
    @Upsert
    fun insertPayment(paymentEntity: PaymentEntity)
    @Query("SELECT * FROM PaymentEntity")
    fun getAllPayments(): PaymentEntity
    @Upsert
    fun insertAddress(addressEntity: AddressEntity)
    @Query("SELECT * FROM AddressEntity")
    fun getAllAddresses(): AddressEntity


}