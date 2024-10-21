package com.example.e_commerce.presentation.order_confirmation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.ADDRESS
import com.example.e_commerce.presentation.ADD_NEW_PAYMENT
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.ORDER_CONFIRMATION
import com.example.e_commerce.presentation.order_confirmation.address.AddressUiState
import com.example.e_commerce.presentation.order_confirmation.payment.PaymentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val addressState = "ADDRESS_STATE"
const val paymentState = "PAYMENT_STATE"

@HiltViewModel
class OrderConfirmationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    logService: LogService,
    accountService: AccountService,
    storageService: StorageService,
    repo: CommerceRepository,
) :
    CommerceViewModel(logService, storageService, accountService, repo) {
    var addressUiState = mutableStateOf(AddressUiState())
        private set
    var paymentUiState = mutableStateOf(PaymentUiState())
    private val address: String
        get() = addressUiState.value.address
    private val city: String
        get() = addressUiState.value.city
    private val cardNumber: String
        get() = paymentUiState.value.cardNumber
    private val cardOwner: String
        get() = paymentUiState.value.cardOwner

    init {
        getCarts()
        addressUiState.value = savedStateHandle.get<AddressUiState>(addressState) ?: AddressUiState()
        paymentUiState.value = savedStateHandle.get<PaymentUiState>(paymentState) ?: PaymentUiState()
        Log.i("Address", "Init View Model: ${addressUiState.value}")
        Log.i("Address", "Init View Model: ${paymentUiState.value}")

    }

    fun onNameChange(newValue: String) {
        addressUiState.value = addressUiState.value.copy(name = newValue)
    }

    fun onCountryChange(newValue: String) {
        addressUiState.value = addressUiState.value.copy(country = newValue)
    }

    fun onCityChange(newValue: String) {
        addressUiState.value = addressUiState.value.copy(city = newValue)
    }

    fun onPhoneNumberChange(newValue: String) {
        addressUiState.value = addressUiState.value.copy(phoneNumber = newValue)
    }

    fun onAddressChange(newValue: String) {
        addressUiState.value = addressUiState.value.copy(address = newValue)
    }

    fun onCardOwnerChange(newValue: String) {
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

    fun onSaveAddressClicked(openScreen: (String) -> Unit) {
        savedStateHandle.set(addressState, addressUiState)
        Log.i("Address", "onSaveAddressClicked: ${addressUiState.value}")
        openScreen(ORDER_CONFIRMATION)
    }

    fun onPaymentClicked(openScreen: (String) -> Unit) {
        openScreen(ADD_NEW_PAYMENT)
    }

    fun onSavePaymentClicked(openScreen: (String) -> Unit) {
        savedStateHandle.set(paymentState,paymentUiState)
        Log.i("Address", "onSaveAddressClicked: ${paymentUiState.value}")
        openScreen(ORDER_CONFIRMATION)
    }
}