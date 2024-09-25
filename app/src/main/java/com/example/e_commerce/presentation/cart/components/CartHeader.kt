package com.example.e_commerce.presentation.cart.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_commerce.ui.theme.E_commerceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartHeader(modifier: Modifier = Modifier) {
    TopAppBar(title = {
        Text(
            "Cart",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = androidx.compose.material.MaterialTheme.colors.background
    ))

}

@Preview
@Composable
private fun CartHeaderPreview() {
    E_commerceTheme {
        CartHeader()
    }
}