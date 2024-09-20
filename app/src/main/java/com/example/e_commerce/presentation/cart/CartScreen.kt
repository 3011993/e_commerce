package com.example.e_commerce.presentation.cart

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.cart.components.CartHeader
import com.example.e_commerce.presentation.cart.components.CartItem
import com.example.e_commerce.presentation.cart.components.CheckOutBottom
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CartScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        val viewModel: CartViewModel = hiltViewModel()
        val carts by viewModel.carts.collectAsState()
        CartContent(
            onRemoveItem = viewModel::removeProductFromCart,
            onIncreaseQuantity = viewModel::addOrUpdateCart,
            onDecreaseQuantity = viewModel::removeProductFromCart,
            carts = carts
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartContent(
    onRemoveItem: (CartModel) -> Unit,
    onIncreaseQuantity: (CartModel) -> Unit,
    onDecreaseQuantity: (CartModel) -> Unit,
    carts: List<CartModel>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            if (carts.isEmpty()) {
                item {
                    Text(
                        text = "the cart is empty ",
                        modifier.align(Alignment.Center)
                    )
                }
            } else {
                stickyHeader(content = {
                    CartHeader()
                })
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

        if (carts.isNotEmpty()) {
            val totalPrice = carts.sumOf { it.price }
            CheckOutBottom(
                totalPrice,
                onCheckOutClick = {},
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    E_commerceTheme {
        val cartsModels = listOf(
            CartModel(
                title = "Bag",
                price = 83.00,
                quantity = 2,
                productId = 1

            )
        )
        CartContent({}, carts = cartsModels, onDecreaseQuantity = {}, onIncreaseQuantity = {})
    }
}