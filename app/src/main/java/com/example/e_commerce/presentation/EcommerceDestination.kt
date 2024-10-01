package com.example.e_commerce.presentation
import com.example.e_commerce.R

interface EcommerceDestination {
    val icon : Int
    val route : String
}
object Home : EcommerceDestination {
    override val icon = R.drawable.home_icon
    override val route: String = "home"
}
object Cart : EcommerceDestination {
    override val icon = R.drawable.cart_icon
    override val route: String = "cart"
}
object WishList : EcommerceDestination {
    override val icon = R.drawable.heart
    override val route: String = "wishlist"
}
object Account : EcommerceDestination {
    override val icon = R.drawable.account_icon
    override val route: String = "account"
}

val ecommerceTabsRowScreen = listOf(Home,WishList,Cart,Account)
