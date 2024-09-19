package com.example.e_commerce.presentation.cart.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce.common.ext.fieldModifier
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CartHeader(modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.background),
        modifier = modifier.fillMaxWidth().shadow(elevation = 4.dp),
    ) {
        Text(
            "Your Shopping Cart",
            modifier = modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun CartHeaderPreview() {
    E_commerceTheme {
        CartHeader()
    }
}