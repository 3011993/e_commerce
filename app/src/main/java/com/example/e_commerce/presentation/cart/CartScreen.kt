package com.example.e_commerce.presentation.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.domain.model.CartItemModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.cart.components.CartItem
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CartScreen(cartProduct: ProductModel, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        val viewModel: CartViewModel = hiltViewModel()
        val cartItemModel = CartItemModel(
            price = cartProduct.price.toDouble(),
            quantity = 1,
            productId = cartProduct.id
        )
        viewModel.addProductToCart(newItem = cartItemModel)

        CartItem(
            cartItemModel = cartItemModel,
            productModel = cartProduct,
            onIncreaseQuantity = {},
            onDecreaseQuantity = {},
            onRemoveItem = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    E_commerceTheme {
        //CartScreen(ProductModel())
    }
}