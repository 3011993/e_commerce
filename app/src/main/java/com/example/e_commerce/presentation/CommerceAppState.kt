package com.example.e_commerce.presentation

import android.content.res.Resources
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.common.snackbar.SnackBarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Stable
class CommerceAppState(
    val snackBarHostState: SnackbarHostState,
    val navController: NavHostController,
    private val snackBarManager: SnackBarManager,
    val resources: Resources,
    coroutineScope: CoroutineScope,
) {
    init {
        coroutineScope.launch {
            snackBarManager.snackBarMessages.filterNotNull().collect { snackBarMessage ->
                val text = snackBarMessage.toMessage(resources)
                snackBarHostState.showSnackbar(text)
                snackBarManager.clearSnackBarState()
            }
        }
    }

    var showBottomNavigation by mutableStateOf(true)

    fun navigate(route: String) {
        val currentRoute = navController.currentDestination?.route
        if (currentRoute != route) {
            navController.navigate(route) { launchSingleTop = true }
        }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        val currentRoute = navController.currentDestination?.route
        if (currentRoute != route) {
            navController.navigate(route) {
                launchSingleTop = true
                popUpTo(popUp) { inclusive = true }
            }
        }
    }

    fun clearAndNavigate(route: String) {
        val currentRoute = navController.currentDestination?.route
        if (currentRoute != route) {
            navController.navigate(route) {
                launchSingleTop = true
                popUpTo(0) { inclusive = true }
            }
        }
    }

    fun popUp() {
        navController.popBackStack()
    }

}