package com.example.e_commerce.presentation.store

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

interface CategoriesEntries {
    val icon: ImageVector
    val category: String
}

object Electronics : CategoriesEntries {
    override val icon: ImageVector = Icons.Filled.Home
    override val category: String = "electronics"
}

object Jewelery : CategoriesEntries {
    override val icon: ImageVector = Icons.Filled.ShoppingCart
    override val category: String = "jewelery"
}

object MenClothing : CategoriesEntries {
    override val icon: ImageVector = Icons.Filled.Face
    override val category: String = "men's clothing"
}

object WomenClothing : CategoriesEntries {
    override val icon: ImageVector = Icons.Filled.Home
    override val category: String = "women's clothing"
}

val ecommerceCategories = listOf(Electronics, Jewelery, MenClothing, WomenClothing)