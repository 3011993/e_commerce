
package com.example.e_commerce.presentation.account.settings

import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.LOGIN_IN_SCREEN
import com.example.e_commerce.presentation.SIGN_UP_SCREEN
import com.example.e_commerce.presentation.Home
import com.example.e_commerce.presentation.SPLASH_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
) : CommerceViewModel(logService) {
    val uiState = accountService.currentUser.map { SettingsUiState(it.isAnonymous) }

    fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOGIN_IN_SCREEN)
    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }
    fun onNavigateBackClicked(clearAndNavigate: (String) -> Unit){
        clearAndNavigate(Home.route)
    }

    fun onDeleteMyAccountClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.deleteAccount()
            restartApp(Home.route)
        }
    }
}
