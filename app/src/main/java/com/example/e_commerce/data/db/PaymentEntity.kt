package com.example.e_commerce.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.e_commerce.domain.model.PaymentModel

@Entity
class PaymentEntity(
    @PrimaryKey val id: Int = 0,
    val cardOwner: String = "",
    val cardNumber: String = "",
    val exp: String = "",
    val cvv: String = "",
)

fun PaymentEntity.toModel() = PaymentModel(
    id =  id,
    cardOwner = cardOwner,
    cardNumber = cardNumber,
    exp = exp,
    cvv = cvv,
)

fun PaymentModel.fromModel() = PaymentEntity(
    id = id,
    cardOwner = cardOwner,
    cardNumber = cardNumber,
    exp = exp,
    cvv = cvv,
)