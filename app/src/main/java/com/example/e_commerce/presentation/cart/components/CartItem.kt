package com.example.e_commerce.presentation.cart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerce.domain.model.CartModel

import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CartItem(
    cartItem: CartModel,
    onIncreaseQuantity: (CartModel) -> Unit,
    onDecreaseQuantity: (CartModel) -> Unit,
    onRemoveItem: (CartModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically // Align items vertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) { // Center image horizontally
            AsyncImage(
                model = cartItem.image,
                contentDescription = cartItem.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp)) // Add space below image
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onDecreaseQuantity(cartItem) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Decrease Quantity"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${cartItem.quantity}")
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { onIncreaseQuantity(cartItem) }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Increase Quantity")
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f)) // Push image and buttons to the left
        Column { // Details on the right
            Text(text = cartItem.title, fontWeight = FontWeight.Bold)
            Text(text = "$${cartItem.price}")
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(onClick = { onRemoveItem(cartItem) }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Remove Item")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    E_commerceTheme {

        val cartItemModel = CartModel(
            price = 99.0,
            quantity = 2,
            productId = 1
        )
        CartItem(
            cartItem = cartItemModel,
            onIncreaseQuantity = {},
            onDecreaseQuantity = {},
            onRemoveItem = {}
        )

    }
}