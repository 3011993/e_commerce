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
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.cart.components.CartItem
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CartScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        val viewModel: CartViewModel = hiltViewModel()
        val carts by viewModel.carts.collectAsState()
        CartContent(
            onRemoveItem = viewModel::removeProductFromCart,
            onIncreaseQuantity = viewModel::addProductToCart,
            onDecreaseQuantity = viewModel::removeProductFromCart,
            carts = carts
        )
    }
}

@Composable
fun CartContent(
    onRemoveItem: (CartModel) -> Unit,
    onIncreaseQuantity: (CartModel) -> Unit,
    onDecreaseQuantity: (CartModel) -> Unit,
    carts: List<CartModel>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        if (carts.isEmpty()) {
            item {
                Text(text = "the cart is empty ")
            }
        } else {
            items(carts) { cart ->
                CartItem(
                    cartItem = cart,
                    onIncreaseQuantity = { onIncreaseQuantity(cart) },
                    onDecreaseQuantity = { onDecreaseQuantity(cart) },
                    onRemoveItem = { onRemoveItem(cart) }
                )

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