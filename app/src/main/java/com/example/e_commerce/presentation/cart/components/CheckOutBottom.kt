package com.example.e_commerce.presentation.cart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CheckOutBottom(totalPrice : Double,onCheckOutClick: () -> Unit,
                   modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth().height(100.dp)
            .shadow(elevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column {
            Text(
                "Total Price: $totalPrice$",
                modifier.padding(start = 16.dp, top = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Button(
                onClick = onCheckOutClick,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Check out")
            }
        }
    }
}

@Preview
@Composable
private fun CheckOutBottomPreview() {
    E_commerceTheme {
        CheckOutBottom(19.0,{})
    }
}