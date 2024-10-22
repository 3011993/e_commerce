package com.example.e_commerce.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.e_commerce.domain.model.AddressModel

@Entity
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val country: String = "",
    val city: String = "",
    val phoneNumber: String = "",
    val address: String = "",
)

fun AddressEntity.toModel() = AddressModel(
    name = name,
    country = country,
    city = city,
    phoneNumber = phoneNumber,
    address = address
)
fun AddressModel.fromModel() = AddressEntity(
    name = name,
    country = country,
    city = city,
    phoneNumber = phoneNumber,
    address = address
)