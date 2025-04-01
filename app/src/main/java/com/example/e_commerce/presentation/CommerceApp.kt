package com.example.e_commerce.presentation

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.presentation.account.login.LoginScreen
import com.example.e_commerce.presentation.account.settings.SettingsScreen
import com.example.e_commerce.presentation.account.settings.SettingsUiState
import com.example.e_commerce.presentation.account.sign_up.SignUpScreen
import com.example.e_commerce.presentation.cart.CartScreen
import com.example.e_commerce.presentation.cart.CartViewModel
import com.example.e_commerce.presentation.cart.components.CheckOutDialog
import com.example.e_commerce.presentation.order_confirmation.payment.AddNewPaymentScreen
import com.example.e_commerce.presentation.order_confirmation.address.AddressScreen
import com.example.e_commerce.presentation.order_confirmation.OrderConfirmationScreen
import com.example.e_commerce.presentation.order_confirmation.order_confirmed.OrderConfirmedScreen
import com.example.e_commerce.presentation.wishlist.WishlistScreen
import com.example.e_commerce.presentation.product_details.ProductDetailsScreen
import com.example.e_commerce.presentation.splash.SplashScreen
import com.example.e_commerce.presentation.home.HomeScreen
import com.example.e_commerce.presentation.order_confirmation.OrderConfirmationViewModel
import com.example.e_commerce.presentation.order_confirmation.order_confirmed.ConfirmOrderDialog
import com.example.e_commerce.presentation.store.StoreContent
import com.example.e_commerce.presentation.store.StoreScreen
import com.example.e_commerce.ui.theme.E_commerceTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun CommerceApp() {
    E_commerceTheme {
        val appState = rememberCommerceAppState()
        val currentBackStack by appState.navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            ecommerceTabsRowScreen.find { it.route == currentDestination?.route } ?: Home
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = it, modifier = Modifier.padding(8.dp),
                    snackbar = { snackBarData ->
                        Snackbar(
                            snackBarData,
                            backgroundColor =
                            MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(32.dp)
                        )
                    }
                )
            },
            scaffoldState = appState.scaffoldState,
            bottomBar = {
                if (appState.showBottomNavigation) {
                    EcommerceBottomNavigation(
                        ecommerceScreens = ecommerceTabsRowScreen,
                        currentScreen = currentScreen,
                        onTabSelected = { newScreen ->
                            appState.navigate(newScreen.route)
                        },
                    )
                }
            }) { innerPadding ->
            NavHost(
                navController = appState.navController,
                startDestination = SPLASH_SCREEN,
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
    composable(Home.route) {
        appState.showBottomNavigation = true
        val viewModel: CartViewModel = hiltViewModel()
        HomeScreen(onProductClick = { product ->
            appState.navigate("$PRODUCT_DETAILS_SCREEN/${product.id}")
        }, onCartButtonClicked = { product ->
            viewModel.addOrUpdateCart(product)
        }, onStoreClicked = {appState.navigate(STORE_SCREEN)})
    }
    composable("$PRODUCT_DETAILS_SCREEN$PRODUCT_ID_ARG") {
        appState.showBottomNavigation = false
        ProductDetailsScreen(onNavigationBackClicked = { appState.popUp() })
    }

    composable(WishList.route) {
        appState.showBottomNavigation = true
        WishlistScreen(clearAndNavigate = { route ->
            appState.navigate(route)
        })
    }
    composable(Cart.route) {
        appState.showBottomNavigation = false
        val viewModel: CartViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState(initial = SettingsUiState(false))
        var showDialog by remember { mutableStateOf(false) }

        CartScreen(onNavigationBackClicked = { appState.popUp() },
            onCheckOutClick = {
                if (uiState.isAnonymousAccount) {
                    showDialog = true
                } else {
                    appState.navigate(ORDER_CONFIRMATION)
                }
            })
        CheckOutDialog(
            showDialog,
            onDismiss = { showDialog = false }) {
            showDialog = false
            appState.navigate(Account.route) }
    }
    composable(Account.route) {
        appState.showBottomNavigation = true
        SettingsScreen(openScreen = { route -> appState.navigate(route) },
            restartApp = { route ->
                appState.clearAndNavigate(route)
            }, clearAndNavigate = { route ->
                appState.clearAndNavigate(route)
            })
    }
    composable(LOGIN_IN_SCREEN) {
        appState.showBottomNavigation = false
        LoginScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(route, popUp)
        })
    }
    composable(SIGN_UP_SCREEN) {
        appState.showBottomNavigation = false
        SignUpScreen(openAndPopUp = { route, popUp ->
            appState.navigateAndPopUp(route, popUp)
        })
    }
    composable(ORDER_CONFIRMATION) {
        var showDialog by remember { mutableStateOf(false) }
        val viewModel : OrderConfirmationViewModel = hiltViewModel()
        OrderConfirmationScreen(openAddressScreen = { route ->
            appState.navigate(route)
        }, openPaymentScreen = { route ->
            appState.navigate(route)
        }, callBack = { isNavigated ->
            showDialog = isNavigated
        }, onNavigationBack = { appState.popUp() })
        ConfirmOrderDialog(
            showDialog,
            onDismiss = { showDialog = false }) {
            appState.navigateAndPopUp(ORDER_CONFIRMED, Home.route)
            viewModel.resetData()
            showDialog = false
        }
    }
    composable(ADDRESS) {
        AddressScreen(openScreen = { route ->
            appState.navigate(route)
        }, onNavigateBack = { appState.popUp() })
    }
    composable(ADD_NEW_PAYMENT) {
        AddNewPaymentScreen(openScreen = { route ->
            appState.navigate(route)
        }, onNavigateBack = { appState.popUp() })
    }
    composable(ORDER_CONFIRMED) {
        OrderConfirmedScreen(openScreen = { route ->
            appState.clearAndNavigate(route)
        }, navigateBack = { route ->
            appState.navigate(route)
        })
    }
    composable(STORE_SCREEN){
        appState.showBottomNavigation = true
        val viewModel: CartViewModel = hiltViewModel()
        StoreScreen(onProductClick = { product ->
            appState.navigate("$PRODUCT_DETAILS_SCREEN/${product.id}")
        }, onCartButtonClicked = { product ->
            viewModel.addOrUpdateCart(product)
        }, onNavigationBackClicked = { appState.popUp() })
    }
}
