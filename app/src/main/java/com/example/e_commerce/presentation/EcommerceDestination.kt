package com.example.e_commerce.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

interface EcommerceDestination {
    val icon : ImageVector
    val route : String
}
object Store : EcommerceDestination {
    override val icon: ImageVector = Icons.Filled.Home
    override val route: String = "store"
}
object Cart : EcommerceDestination {
    override val icon: ImageVector = Icons.Filled.ShoppingCart
    override val route: String = "cart"
}
object Categories : EcommerceDestination {
    override val icon: ImageVector = Icons.Filled.Face
    override val route: String = "categories"
}
object Account : EcommerceDestination {
    override val icon: ImageVector = Icons.Filled.Home
    override val route: String = "account"
}

val ecommerceTabsRowScreen = listOf(Store,Cart,Categories,Account)
