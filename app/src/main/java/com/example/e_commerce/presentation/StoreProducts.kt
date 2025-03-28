package com.example.e_commerce.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce.R
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.home.HomeContent
import com.example.e_commerce.presentation.home.components.ProductItem
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun StoreProducts(
    products: List<ProductModel>,
    onProductClick: (ProductModel) -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit,
    onFavouriteClicked: (ProductModel) -> Unit,
    productsInStock: Map<String, Boolean>,
    isConnected: Boolean,
    modifier: Modifier = Modifier,
) {
    var showSnackBar by remember { mutableStateOf(true) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item(span = { GridItemSpan(2) }) {
            if (showSnackBar && !isConnected) {
                SnackBarManager.showMessage(R.string.offline_message)
                showSnackBar = false
            }
        }
        items(products, key = { it.id }) { product ->
            val inStock = productsInStock[product.id.toString()] ?: true
            ProductItem(
                product = product,
                onProductClick,
                onCartButtonClicked,
                isInStock = inStock,
                onFavouriteClicked,
            )
        }
    }
}

@Preview
@Composable
private fun StoreProductsPreview() {
    E_commerceTheme {
        val productsList = listOf(
            ProductModel(
                title = "Bag",
                price = "80.44",
                category = "",
                description = "",
                id = 0,
                image = ""
            ),
            ProductModel(
                title = "Shoe",
                price = "11.44",
                category = "",
                description = "",
                id = 1,
                image = ""
            ),
            ProductModel(
                title = "Hard Disk",
                price = "11.44",
                category = "",
                description = "",
                id = 2,
                image = ""
            )

        )
        StoreProducts(
            products = productsList, {}, {}, {},
            productsInStock = emptyMap(), isConnected = true
        )
    }
}