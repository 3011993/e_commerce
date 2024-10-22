package com.example.e_commerce.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentModel(
    val cardOwner: String = "",
    val cardNumber: String ="",
    val exp : String = "",
    val cvv : String = "",
) : Parcelable
