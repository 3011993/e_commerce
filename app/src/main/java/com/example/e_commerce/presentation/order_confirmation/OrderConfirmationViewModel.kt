package com.example.e_commerce.presentation.order_confirmation

import androidx.compose.runtime.mutableStateOf
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

@HiltViewModel
class OrderConfirmationViewModel @Inject constructor(
    logService: LogService,
    accountService: AccountService,
    storageService: StorageService,
    repo: CommerceRepository,
) : CommerceViewModel(logService, storageService, accountService, repo) {

    init {
        getCarts()
    }

    fun onAddressClicked(openScreen: (String) -> Unit) {
        openScreen(ADDRESS)
    }
    fun onPaymentClicked(openScreen: (String) -> Unit) {
        openScreen(ADD_NEW_PAYMENT)
    }
    fun onPlaceOrderClicked(showDialog : Boolean,openScreenAndPopup: (String, String) -> Unit) {
//        if (address.isBlank()) {
//            SnackBarManager.showMessage("address is empty")
//        } else if (city.isBlank()) {
//            SnackBarManager.showMessage("city is empty")
//        } else {
//            openScreenAndPopup(ORDER_CONFIRMED, Home.route)
//        }
        openScreenAndPopup(ORDER_CONFIRMED, Home.route)
    }

    fun onContinueShoppingClicked(openScreen: (String) -> Unit) {
        openScreen(Home.route)
    }
}