package com.example.e_commerce.presentation.store

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.e_commerce.R

interface CategoriesEntries {
    val icon: Int
    val category: String
}

object Electronics : CategoriesEntries {
    override val icon = R.drawable.electronics
    override val category: String = "electronics"
}

object Jewelery : CategoriesEntries {
    override val icon = R.drawable.jewelry_icon
    override val category: String = "jewelery"
}

object MenClothing : CategoriesEntries {
    override val icon = R.drawable.clothing
    override val category: String = "men's clothing"
}

object WomenClothing : CategoriesEntries {
    override val icon = R.drawable.clothing_women
    override val category: String = "women's clothing"
}

val ecommerceCategories = listOf(Electronics, Jewelery, MenClothing, WomenClothing)