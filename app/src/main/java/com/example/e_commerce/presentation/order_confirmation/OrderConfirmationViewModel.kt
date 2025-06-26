package com.example.e_commerce.presentation.order_confirmation

import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.domain.model.AddressModel
import com.example.e_commerce.domain.model.PaymentModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.ADDRESS
import com.example.e_commerce.presentation.ADD_NEW_PAYMENT
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.Home
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun onPlaceOrderClicked(
        address: AddressModel,
        paymentModel: PaymentModel,
        callBack: (Boolean) -> Unit
    ) {
        if (address.address.isBlank()) {
            SnackBarManager.showMessage("Please Add your Delivery Address")
            callBack(false)
            return
        }
        if (paymentModel.cardNumber.isBlank()) {
            SnackBarManager.showMessage("Please add Your Payment Details")
            callBack(false)
            return
        }
        callBack(true)
    }


    fun onContinueShoppingClicked(openScreen: (String) -> Unit) {
        openScreen(Home.route)
    }
}