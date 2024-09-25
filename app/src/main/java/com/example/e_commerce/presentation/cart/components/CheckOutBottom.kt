package com.example.e_commerce.presentation.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CheckOutBottom(
    totalPrice: Double, onCheckOutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .shadow(elevation = 4.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total Price",
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
                Text(
                    "$totalPrice",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Button(
                onClick = onCheckOutClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(75.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RectangleShape
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
        CheckOutBottom(19.0, {})
    }
}