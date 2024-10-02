package com.example.e_commerce.presentation.cart

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.presentation.cart.components.CartHeader
import com.example.e_commerce.presentation.cart.components.CheckOutBottom
import com.example.e_commerce.presentation.cart.components.CartItem
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.ui.theme.secondaryOnBackGround

@Composable
fun CartScreen(onNavigationBackClicked: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        val viewModel: CartViewModel = hiltViewModel()
        val carts by viewModel.carts.collectAsState()
        CartContent(
            onRemoveItem = viewModel::removeProductFromCart,
            onIncreaseQuantity = viewModel::addOrUpdateCart,
            onDecreaseQuantity = viewModel::removeProductFromCart,
            onNavigationBackClicked = onNavigationBackClicked,
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
    onNavigationBackClicked: () -> Unit,
    carts: List<CartModel>,
    modifier: Modifier = Modifier,
) {
    var showLoading by remember { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            if (carts.isEmpty()) {
                showLoading = true
            } else {
                showLoading = false
                stickyHeader(content = {
                    CartHeader(onNavigationBackClicked = onNavigationBackClicked)
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

        if (showLoading) {
            Text(
                "You dont have any items in your cart",
                style = MaterialTheme.typography.bodyMedium.copy(color = secondaryOnBackGround),
                modifier = modifier.align(Alignment.Center)
            )
        } else {
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
        CartContent(
            {},
            carts = cartsModels,
            onDecreaseQuantity = {},
            onIncreaseQuantity = {},
            onNavigationBackClicked = {})
    }
}