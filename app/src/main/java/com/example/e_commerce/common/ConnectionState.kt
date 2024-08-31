package com.example.e_commerce.common

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}