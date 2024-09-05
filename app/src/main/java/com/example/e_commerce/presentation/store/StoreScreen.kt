package com.example.e_commerce.presentation.store

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.common.ConnectionState
import com.example.e_commerce.common.composable.rememberConnectivityState
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.ScreenState
import com.example.e_commerce.presentation.store.components.ProductItem
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun StoreScreen(
    onProductClick: (ProductModel) -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: StoreViewModel = hiltViewModel()
    val state by viewModel.allProducts.collectAsState()
    StoreContent(
        state = state, onProductClick,onCartButtonClicked, modifier
    )
}

@Composable
fun StoreContent(
    state: ScreenState<List<ProductModel>>,
    onProductClick: (ProductModel) -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val connection by rememberConnectivityState()
    val isConnected by remember(key1 = connection) {
        derivedStateOf { connection === ConnectionState.Available }
    }
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is ScreenState.Error -> {
                ProductsLazyVerticalGrid(
                    products = state.data ?: emptyList(),
                    onProductClick = onProductClick,
                    onCartButtonClicked = onCartButtonClicked,
                    isConnected = isConnected
                )
                Log.i("Store Screen", state.message ?: "An unexpected error occurred")
            }

            is ScreenState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ScreenState.Success -> {
                ProductsLazyVerticalGrid(
                    products = state.data,
                    onProductClick = onProductClick,
                    onCartButtonClicked = onCartButtonClicked,
                    isConnected = isConnected
                )
            }
        }
    }
}

@Composable
fun ProductsLazyVerticalGrid(
    products: List<ProductModel>,
    onProductClick: (ProductModel) -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit,
    isConnected: Boolean,
    modifier: Modifier = Modifier,
) {
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
            if (isConnected) Text(
                text = "Connected",
                modifier
                    .fillMaxWidth()
                    .background(Color.Green),
                textAlign = TextAlign.Center
            ) else Text(
                text = "Please Connect to your Internet!",
                modifier
                    .fillMaxWidth()
                    .background(Color.Red),
                textAlign = TextAlign.Center
            )
        }
        items(products) { product ->
            ProductItem(product = product, onProductClick, onCartButtonClicked)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StoreScreenPreview() {
    E_commerceTheme {
        //StoreContent()
    }
}