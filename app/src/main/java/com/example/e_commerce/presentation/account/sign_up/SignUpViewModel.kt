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

package com.example.e_commerce.presentation.account.sign_up

import androidx.compose.runtime.mutableStateOf
import com.example.e_commerce.R.string as AppText
import com.example.e_commerce.common.ext.isValidEmail
import com.example.e_commerce.common.ext.isValidPassword
import com.example.e_commerce.common.ext.passwordMatches
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.Account
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.SIGN_UP_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    logService: LogService,
    repo : CommerceRepository,
    storageService: StorageService,
    private val accountService: AccountService,
) : CommerceViewModel(logService,storageService,accountService,repo) {

    var uiStateSignUp = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiStateSignUp.value.email
    private val password
        get() = uiStateSignUp.value.password

    fun onEmailChange(newValue: String) {
        uiStateSignUp.value = uiStateSignUp.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiStateSignUp.value = uiStateSignUp.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiStateSignUp.value = uiStateSignUp.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackBarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackBarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiStateSignUp.value.repeatPassword)) {
            SnackBarManager.showMessage(AppText.password_match_error)
            return
        }
       launchCatching{
            accountService.linkAccount(email, password)
        }
        openAndPopUp(Account.route, SIGN_UP_SCREEN)

    }
}
