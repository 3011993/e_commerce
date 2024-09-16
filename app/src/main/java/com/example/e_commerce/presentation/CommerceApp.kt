package com.example.e_commerce.presentation

import android.content.res.Resources
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.presentation.account.login.LoginScreen
import com.example.e_commerce.presentation.account.settings.SettingsScreen
import com.example.e_commerce.presentation.account.sign_up.SignUpScreen
import com.example.e_commerce.presentation.cart.CartScreen
import com.example.e_commerce.presentation.cart.CartViewModel
import com.example.e_commerce.presentation.categories.CategoriesScreen
import com.example.e_commerce.presentation.product_details.ProductDetailsScreen
import com.example.e_commerce.presentation.splash.SplashScreen
import com.example.e_commerce.presentation.store.StoreScreen
import com.example.e_commerce.ui.theme.E_commerceTheme
import kotlinx.coroutines.CoroutineScope
import java.util.UUID

@Composable
fun CommerceApp() {
    E_commerceTheme {
        val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
        val appState = rememberCommerceAppState()
        val currentBackStack by appState.navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            ecommerceTabsRowScreen.find { it.route == currentDestination?.route } ?: Store
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = it, modifier = Modifier.padding(8.dp),
                    snackbar = { snackBarData ->
                        Snackbar(snackBarData, contentColor = MaterialTheme.colors.onPrimary)
                    }
                )
            },
            scaffoldState = appState.scaffoldState,
            bottomBar = {
                EcommerceBottomNavigation(
                    ecommerceScreens = ecommerceTabsRowScreen,
                    currentScreen = currentScreen,
                    onTabSelected = { newScreen ->
                        appState.navigate(newScreen.route)
                    },
                )
            }) { innerPadding ->
            NavHost(
                navController = appState.navController,
                startDestination = Store.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                commerceGraph(appState = appState)
            }
        }
    }
}

@Composable
fun rememberCommerceAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackBarManager: SnackBarManager = SnackBarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember(scaffoldState, navController, snackBarManager, resources, coroutineScope) {
    CommerceAppState(scaffoldState, navController, snackBarManager, resources, coroutineScope)
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.commerceGraph(appState: CommerceAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(route, popUp)
        })
    }
    composable(Store.route) {
        val viewModel: CartViewModel = hiltViewModel()
        StoreScreen(onProductClick = { product ->
            appState.navigate("$PRODUCT_DETAILS_SCREEN/${product.id}")
        }, onCartButtonClicked = { product ->
            val cartItemModel = CartModel(
                cartId = UUID.randomUUID().toString(),
                price = product.price.toDouble(),
                quantity = 1,
                productId = product.id,
                originalPrice = product.price.toDouble(),
                title = product.title,
                image = product.image
            )
            if (cartItemModel.cartId.isEmpty()) {
                viewModel.addOrUpdateCart(cartItemModel)
            } else {
                viewModel.addProductToCart(cartItemModel)
            }
            appState.navigate(Cart.route)
        })
    }
    composable("$PRODUCT_DETAILS_SCREEN$PRODUCT_ID_ARG") {
        ProductDetailsScreen()
    }

    composable(Categories.route) {
        CategoriesScreen()
    }
    composable(Cart.route) {
        CartScreen()
    }
    composable(Account.route) {
        SettingsScreen(openScreen = { route -> appState.navigate(route) },
            restartApp = { route ->
                appState.clearAndNavigate(route)
            })
    }
    composable(LOGIN_IN_SCREEN) {
        LoginScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(route, popUp)
        })
    }
    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(route, popUp)
        })
    }
}

@Preview(showBackground = true)
@Composable
fun CommerceAppPreview() {
    E_commerceTheme {
        CommerceApp()
    }
}
