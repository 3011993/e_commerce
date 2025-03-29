package com.example.e_commerce.presentation.home

import com.example.e_commerce.R

interface CategoriesEntries {
    val icon: Int
    val category: String
}

object AllProducts : CategoriesEntries {
    override val icon: Int = R.drawable.box_unpacked_svgrepo_com
    override val category: String ="All"

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