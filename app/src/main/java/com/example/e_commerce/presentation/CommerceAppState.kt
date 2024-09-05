package com.example.e_commerce.presentation

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.common.snackbar.SnackBarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Stable
class CommerceAppState (
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val snackBarManager : SnackBarManager,
    val resources : Resources,
    coroutineScope: CoroutineScope
    ){
    init {
        coroutineScope.launch {
            snackBarManager.snackBarMessages.filterNotNull().collect{ snackBarMessage ->
                val text = snackBarMessage.toMessage(resources)
                scaffoldState.snackbarHostState.showSnackbar(text)
                snackBarManager.clearSnackBarState()
            }
        }
    }
    fun navigate(route : String){
        navController.navigate(route) {launchSingleTop = true}
    }
    fun navigateAndPopUp(route : String, popUp : String){
        navController.navigate(route){
            launchSingleTop = true
            popUpTo(popUp) {inclusive = true}
        }
    }
    fun clearAndNavigate(route : String){
        navController.navigate(route){
            launchSingleTop = true
            popUpTo(0) {inclusive = true}
        }
    }
    fun popUp(){
        navController.popBackStack()
    }

}