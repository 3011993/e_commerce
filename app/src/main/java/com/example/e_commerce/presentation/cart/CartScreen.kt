package com.example.e_commerce.presentation.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.domain.model.CartItemModel
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.cart.components.CartItem
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CartScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        val viewModel: CartViewModel = hiltViewModel()
        val carts by viewModel.carts.collectAsState()
        CartContent(carts = carts)
    }
}

@Composable
fun CartContent(carts: List<CartModel>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        if (carts.isEmpty()) {
            item {
                Text(text = "the cart is empty ")
            }
        } else {
            items(carts) { cart ->
                cart.cartItems.forEach { cartItem ->
                    CartItem(
                        cartItem = cartItem,
                        onIncreaseQuantity = {},
                        onDecreaseQuantity = {},
                        onRemoveItem = {}
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    E_commerceTheme {
        //CartScreen(ProductModel())
    }
}