package com.example.e_commerce.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteEntity(@PrimaryKey val productId : Int, var isFavourite : Boolean)
