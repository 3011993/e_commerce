package com.example.e_commerce.presentation.order_confirmation.payment

import androidx.compose.runtime.mutableStateOf
import com.example.e_commerce.domain.model.PaymentModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.BaseCommerceViewModel
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.ORDER_CONFIRMATION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    logService: LogService, private val repo: CommerceRepository
) : BaseCommerceViewModel(logService) {
    var paymentModel = mutableStateOf(PaymentModel())
        private set
    private val cardNumber: String
        get() = paymentModel.value.cardNumber
    private val cardOwner: String
        get() = paymentModel.value.cardOwner
    private val cvv: String
        get() = paymentModel.value.cvv

    init {
        getPayment()
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

    fun onSavePaymentClicked(openScreen: (String) -> Unit) {
        launchCatching(dispatcher = Dispatchers.IO) {
            repo.savePayment(paymentModel.value)
        }
        openScreen(ORDER_CONFIRMATION)

    }

    private fun getPayment() {
        launchCatching(dispatcher = Dispatchers.IO) {
            paymentModel.value = repo.getPayments()

        }
    }

}