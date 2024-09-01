package com.example.e_commerce.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommerceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(vararg product : ProductEntity)
    @Query("SELECT * FROM PRODUCTENTITY")
    fun getAllProducts () : List<ProductEntity>



}