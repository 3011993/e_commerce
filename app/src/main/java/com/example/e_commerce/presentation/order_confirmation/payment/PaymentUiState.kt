package com.example.e_commerce.presentation.order_confirmation.payment

data class PaymentUiState(
    val cardOwner: String = "",
    val cardNumber: String ="",
    val exp : String = "",
    val cvv : String = "",
)
