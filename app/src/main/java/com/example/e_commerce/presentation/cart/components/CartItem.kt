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
import com.example.e_commerce.domain.model.CartItemModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CartItem(
    cartItemModel: CartItemModel,
    productModel: ProductModel,
    onIncreaseQuantity: (CartItemModel) -> Unit,
    onDecreaseQuantity: (CartItemModel) -> Unit,
    onRemoveItem: (CartItemModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically // Align items vertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) { // Center image horizontally
            AsyncImage(
                model = productModel.image,
                contentDescription = productModel.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp)) // Add space below image
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onDecreaseQuantity(cartItemModel) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Decrease Quantity"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${cartItemModel.quantity}")
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { onIncreaseQuantity(cartItemModel) }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Increase Quantity")
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f)) // Push image and buttons to the left
        Column { // Details on the right
            Text(text = productModel.title, fontWeight = FontWeight.Bold)
            Text(text = "$${cartItemModel.price}")
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(onClick = { onRemoveItem(cartItemModel) }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Remove Item")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    E_commerceTheme {

        val cartItemModel = CartItemModel(
            price = 98.0,
            quantity = 2,
            productId = 1
        )
        val product = ProductModel(
            category = "Electronics",
            price = "140 EGP",
            image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            description = "this is ay 7age",
            title = "Bag",
            id = 0
        )
        CartItem(
            cartItemModel = cartItemModel,
            productModel = product,
            onIncreaseQuantity = {},
            onDecreaseQuantity = {},
            onRemoveItem = {}
        )

    }
}