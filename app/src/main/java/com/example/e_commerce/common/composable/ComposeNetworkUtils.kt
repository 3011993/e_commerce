package com.example.e_commerce.common.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.example.e_commerce.common.ConnectionState
import com.example.e_commerce.common.currentConnectivityState
import com.example.e_commerce.common.observeConnectionAsFlow

@Composable
fun rememberConnectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    return produceState(initialValue = context.currentConnectivityState) {
        context.observeConnectionAsFlow().collect {
            value = it
        }
    }
}