package com.example.e_commerce.presentation.order_confirmation.address

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressUiState(
    val name: String = "",
    val country: String = "",
    val city: String = "",
    val phoneNumber: String ="",
    val address: String = "",
) : Parcelable
