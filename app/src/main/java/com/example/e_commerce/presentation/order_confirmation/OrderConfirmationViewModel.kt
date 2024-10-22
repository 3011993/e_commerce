package com.example.e_commerce.presentation.order_confirmation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.e_commerce.common.ext.isValidCvv
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.ADDRESS
import com.example.e_commerce.presentation.ADD_NEW_PAYMENT
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.Home
import com.example.e_commerce.presentation.ORDER_CONFIRMATION
import com.example.e_commerce.presentation.ORDER_CONFIRMED
import com.example.e_commerce.domain.model.AddressModel
import com.example.e_commerce.domain.model.PaymentModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

const val addressState = "ADDRESS_STATE"
const val paymentState = "PAYMENT_STATE"

@HiltViewModel
class OrderConfirmationViewModel @Inject constructor(
    logService: LogService,
    accountService: AccountService,
    storageService: StorageService,
    private val repo: CommerceRepository,
) :
    CommerceViewModel(logService, storageService, accountService, repo) {
    var addressModel = mutableStateOf(AddressModel())
        private set
    var paymentModel = mutableStateOf(PaymentModel())
    private val address: String
        get() = addressModel.value.address
    private val city: String
        get() = addressModel.value.city
    private val cardNumber: String
        get() = paymentModel.value.cardNumber
    private val cardOwner: String
        get() = paymentModel.value.cardOwner
    private val cvv : String
        get() = paymentModel.value.cvv

    init {
        getCarts()
        getPayment()
        getAddress()
    }

    fun onNameChange(newValue: String) {
        addressModel.value = addressModel.value.copy(name = newValue)
    }

    fun onCountryChange(newValue: String) {
        addressModel.value = addressModel.value.copy(country = newValue)
    }

    fun onCityChange(newValue: String) {
        addressModel.value = addressModel.value.copy(city = newValue)
    }

    fun onPhoneNumberChange(newValue: String) {
        addressModel.value = addressModel.value.copy(phoneNumber = newValue)
    }

    fun onAddressChange(newValue: String) {
        addressModel.value = addressModel.value.copy(address = newValue)
    }

    fun onCardOwnerChange(newValue: String) {
        paymentModel.value = paymentModel.value.copy(cardOwner = newValue)
    }

    fun onCardNumberChange(newValue: String) {
        paymentModel.value = paymentModel.value.copy(cardNumber = newValue)
    }

    fun onExpChange(newValue: String) {
        paymentModel.value = paymentModel.value.copy(exp = newValue)
    }

    fun onCvvChange(newValue: String) {
        paymentModel.value = paymentModel.value.copy(cvv = newValue)
    }


    fun onAddressClicked(openScreen: (String) -> Unit) {
        openScreen(ADDRESS)
    }

    fun onSaveAddressClicked(openScreen: (String) -> Unit) {
        launchCatching(dispatcher = Dispatchers.IO) {
            repo.saveAddress(addressModel.value)
        }
        openScreen(ORDER_CONFIRMATION)
    }

    fun onPaymentClicked(openScreen: (String) -> Unit) {
        openScreen(ADD_NEW_PAYMENT)
    }

    fun onSavePaymentClicked(openScreen: (String) -> Unit) {
        launchCatching(dispatcher = Dispatchers.IO) {
            repo.savePayment(paymentModel.value)
        }
        openScreen(ORDER_CONFIRMATION)

    }

    fun onPlaceOrderClicked(openScreenAndPopup: (String, String) -> Unit) {
        if (address.isBlank()) {
            SnackBarManager.showMessage("address is empty")
        } else if (city.isBlank()) {
            SnackBarManager.showMessage("city is empty")
        } else {
            openScreenAndPopup(ORDER_CONFIRMED, Home.route)
        }
    }

    fun onContinueShoppingClicked(openScreen: (String) -> Unit) {
        openScreen(Home.route)
    }
    fun getPayment() {
        launchCatching(dispatcher = Dispatchers.IO) {
            addressModel.value = repo.getAddress()
        }
    }
    fun getAddress(){
        launchCatching(dispatcher = Dispatchers.IO) {
            paymentModel.value = repo.getPayments()
        }
    }
}