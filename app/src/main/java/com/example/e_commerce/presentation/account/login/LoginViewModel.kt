/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.e_commerce.presentation.account.login

import androidx.compose.runtime.mutableStateOf
import com.example.e_commerce.R.string as AppText
import com.example.e_commerce.common.ext.isValidEmail
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.Account
import com.example.e_commerce.presentation.BaseCommerceViewModel
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.LOGIN_IN_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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
