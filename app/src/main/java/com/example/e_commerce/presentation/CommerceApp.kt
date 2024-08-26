package com.example.e_commerce.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce.presentation.account.AccountScreen
import com.example.e_commerce.presentation.cart.CartScreen
import com.example.e_commerce.presentation.categories.CategoriesScreen
import com.example.e_commerce.presentation.store.StoreScreen
import com.example.e_commerce.presentation.store.StoreViewModel
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

@Composable
fun EcommerceNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Store.route, modifier = modifier) {
        composable(Store.route) {
            StoreScreen()
        }
        composable(Categories.route) {
            CategoriesScreen()
        }
        composable(Cart.route) {
            CartScreen()
        }
        composable(Account.route) {
            AccountScreen()
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


@Preview(showBackground = true)
@Composable
fun CommerceAppPreview() {
    E_commerceTheme {
        CommerceApp()
    }
}
