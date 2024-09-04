package com.example.e_commerce.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.account.login.LoginScreen
import com.example.e_commerce.presentation.account.settings.SettingsScreen
import com.example.e_commerce.presentation.account.sign_up.SignUpScreen
import com.example.e_commerce.presentation.cart.CartScreen
import com.example.e_commerce.presentation.categories.CategoriesScreen
import com.example.e_commerce.presentation.product_details.ProductDetailsScreen
import com.example.e_commerce.presentation.store.StoreScreen
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CommerceApp() {
    E_commerceTheme {
        val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            ecommerceTabsRowScreen.find { it.route == currentDestination?.route } ?: Store
        Scaffold(
            bottomBar = {
                EcommerceBottomNavigation(
                    ecommerceScreens = ecommerceTabsRowScreen,
                    currentScreen = currentScreen,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    modifier = Modifier.padding(bottom = systemBarsPadding.calculateBottomPadding())
                )
            }) { innerPadding ->
            EcommerceNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EcommerceNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Store.route, modifier = modifier) {
        composable(Store.route) {
            StoreScreen(onProductClick = { product ->
                navController.navigateSingleTopTo("$PRODUCT_DETAILS_SCREEN/${product.id}")
            }, onCartButtonClicked = { product ->
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "product",
                    value = product
                )
                navController.navigateSingleTopTo(Cart.route)
            })
        }
        composable("$PRODUCT_DETAILS_SCREEN$PRODUCT_ID_ARG") {
            ProductDetailsScreen()
        }

        composable(Categories.route) {
            CategoriesScreen()
        }
        composable(Cart.route) {
            val cartProduct =
                navController.previousBackStackEntry?.savedStateHandle?.get<ProductModel>("product")
            cartProduct?.let { CartScreen(it) }
        }
        composable(Account.route) {
            SettingsScreen(openScreen = { route -> navController.navigateSingleTopTo(route) },
                restartApp = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                })
        }
        composable(LOGIN_IN_SCREEN) {
            LoginScreen(openAndPopUp = { route, popUp ->
                navController.navigateAndPopUp(
                    route,
                    popUp
                )
            })
        }
        composable(SIGN_UP_SCREEN) {
            SignUpScreen(openAndPopUp = { route, popUp ->
                navController.navigateAndPopUp(
                    route,
                    popUp
                )
            })
        }

    }

}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

fun NavHostController.navigateAndPopUp(route: String, popUp: String) {
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) { inclusive = true }
    }
}


@Preview(showBackground = true)
@Composable
fun CommerceAppPreview() {
    E_commerceTheme {
        CommerceApp()
    }
}
