package com.example.e_commerce.domain.model

data class ServerCartItem(val id: String, val amount: Long)
data class PaymentRequestBody(val items: List<ServerCartItem>)