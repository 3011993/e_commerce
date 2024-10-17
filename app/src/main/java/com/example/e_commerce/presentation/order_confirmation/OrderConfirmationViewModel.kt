package com.example.e_commerce.presentation.order_confirmation

import androidx.compose.runtime.mutableStateOf
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.ADDRESS
import com.example.e_commerce.presentation.ADD_NEW_PAYMENT
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.order_confirmation.address.AddressUiState
import com.example.e_commerce.presentation.order_confirmation.payment.PaymentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderConfirmationViewModel @Inject constructor(
    logService: LogService,
    accountService: AccountService,
    storageService: StorageService,
    repo: CommerceRepository,
) :
    CommerceViewModel(logService, storageService, accountService, repo) {
    var addressUiState = mutableStateOf(AddressUiState())
        private set
    var paymentUiState = mutableStateOf(PaymentUiState())

    init {
        getCarts()
    }

    fun onNameChange(newValue: String) {
        addressUiState.value = addressUiState.value.copy(name = newValue)
    }
    fun onCountryChange(newValue: String){
        addressUiState.value = addressUiState.value.copy(country = newValue)
    }
    fun onCityChange(newValue: String){
        addressUiState.value = addressUiState.value.copy(city = newValue)
    }
    fun onPhoneNumberChange(newValue: String){
        addressUiState.value = addressUiState.value.copy(phoneNumber = newValue)
    }
    fun onAddressChange(newValue: String){
        addressUiState.value = addressUiState.value.copy(address = newValue)
    }
    fun onCardOwnerChange(newValue: String){
        paymentUiState.value = paymentUiState.value.copy(cardOwner = newValue)
    }
    fun onCardNumberChange(newValue: String) {
        paymentUiState.value = paymentUiState.value.copy(cardNumber = newValue)
    }
    fun onExpChange(newValue: String) {
        paymentUiState.value = paymentUiState.value.copy(exp = newValue)
    }

    fun onCvvChange(newValue: String) {
        paymentUiState.value = paymentUiState.value.copy(cvv = newValue)
    }


    fun onAddressClicked(openScreen: (String) -> Unit) {
        openScreen(ADDRESS)
    }

    fun onPaymentClicked(openScreen: (String) -> Unit) {
        openScreen(ADD_NEW_PAYMENT)
    }
}