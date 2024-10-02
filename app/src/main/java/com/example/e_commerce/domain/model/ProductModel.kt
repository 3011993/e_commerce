package com.example.e_commerce.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: String,
    val title: String,
    var isFavorite: Boolean = false
) : Parcelable
