package com.example.e_commerce.presentation.order_confirmation.stripe.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.e_commerce.R
import com.example.e_commerce.common.composable.CustomizedCommerceButton

@Composable
fun PayButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CustomizedCommerceButton(
        enabled = enabled,
        action = onClick,
        text = R.string.pay_now_button,
        modifier = modifier
    )
}
