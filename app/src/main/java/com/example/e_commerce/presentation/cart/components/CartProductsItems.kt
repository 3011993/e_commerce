package com.example.e_commerce.presentation.cart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.e_commerce.domain.model.ProductCartModel

@Composable
fun CartProductsItems(products: List<ProductCartModel>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(products) { product ->
            CartProductItem(product = product)
        }
    }
}

@Composable
fun CartProductItem(product: ProductCartModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "${product.productId}")
        Text(text = "${product.quantity}")
    }

}