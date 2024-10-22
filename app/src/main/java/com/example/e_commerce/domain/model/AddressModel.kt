package com.example.e_commerce.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressModel(
    val name: String = "",
    val country: String = "",
    val city: String = "",
    val phoneNumber: String ="",
    val address: String = "",
) : Parcelable
