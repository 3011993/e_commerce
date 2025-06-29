package com.example.e_commerce.presentation.order_confirmation.stripe.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PayButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        onClick = onClick
    ) {
        Text("Pay now")
    }
}
