package com.example.e_commerce.presentation.order_confirmation.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentUiState(
    val cardOwner: String = "",
    val cardNumber: String ="",
    val exp : String = "",
    val cvv : String = "",
) : Parcelable
