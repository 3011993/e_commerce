package com.example.e_commerce.presentation.account.login

import androidx.compose.runtime.mutableStateOf
import com.example.e_commerce.common.ext.isValidEmail
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.presentation.Account
import com.example.e_commerce.presentation.BaseCommerceViewModel
import com.example.e_commerce.presentation.LOGIN_IN_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.e_commerce.R.string as AppText

@HiltViewModel
class LoginViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
) : BaseCommerceViewModel(logService) {
    var uiStateLogIn = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiStateLogIn.value.email
    private val password
        get() = uiStateLogIn.value.password

    fun onEmailChange(newValue: String) {
        uiStateLogIn.value = uiStateLogIn.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiStateLogIn.value = uiStateLogIn.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackBarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackBarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(Account.route, LOGIN_IN_SCREEN)
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackBarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            SnackBarManager.showMessage(AppText.recovery_email_sent)
        }
    }
}
