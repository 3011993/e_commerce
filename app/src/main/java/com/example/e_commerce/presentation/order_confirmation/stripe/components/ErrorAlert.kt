package com.example.e_commerce.presentation.order_confirmation.stripe.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
 fun ErrorAlert(
    errorMessage: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = "Error occurred during checkout")
        },
        text = {
            Text(text = errorMessage)
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onDismiss) {
                Text(text = "Ok")
            }
        }
    )
}