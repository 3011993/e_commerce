package com.example.e_commerce.presentation.store

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.common.ConnectionState
import com.example.e_commerce.common.composable.rememberConnectivityState
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.ScreenState
import com.example.e_commerce.presentation.store.components.CategoriesSection
import com.example.e_commerce.presentation.store.components.ProductItem
import com.example.e_commerce.presentation.store.components.SearchBar
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.ui.theme.secondaryOnBackGround
import com.example.e_commerce.R.string as AppText

@Composable
fun StoreScreen(
    onProductClick: (ProductModel) -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: StoreViewModel = hiltViewModel()
    val state by viewModel.allProducts.collectAsState()
    StoreContent(
        state = state,
        onProductClick = onProductClick,
        onCartButtonClicked = onCartButtonClicked,
        getAllProducts = viewModel::getAllProducts,
        searchPrefix = viewModel::searchProducts,
        onCategorySelected = viewModel::getProductsByCategory,
        modifier
    )
}

@Composable
fun StoreContent(
    state: ScreenState<List<ProductModel>>,
    onProductClick: (ProductModel) -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit,
    getAllProducts: () -> Unit,
    searchPrefix: (String) -> List<ProductModel>,
    onCategorySelected: (CategoriesEntries) -> Unit,
    modifier: Modifier = Modifier,
) {
    val connection by rememberConnectivityState()
    var previousConnection by remember { mutableStateOf<ConnectionState?>(null) }
    val isConnected by remember(key1 = connection) {
        derivedStateOf { connection === ConnectionState.Available }
    }
    var showRefreshButton by remember { mutableStateOf(false) }
    var displayedProducts by remember { mutableStateOf<List<ProductModel>>(emptyList()) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(key1 = connection) {
        if (previousConnection != null && previousConnection != connection) {
            if (isConnected) {
                showRefreshButton = true
            }
        } else {
            showRefreshButton = false
        }
        previousConnection = connection
    }
    LaunchedEffect(state) {
        displayedProducts = when (state) {
            is ScreenState.Success -> state.data
            is ScreenState.Error -> state.data ?: emptyList()
            else -> emptyList()
        }
    }
    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Text(
                "Hello", style = MaterialTheme.typography.labelLarge,
                modifier = modifier.padding(start = 16.dp,top = 8.dp)
            )
            Text(
                "Welcome to our Store",
                style = MaterialTheme.typography.bodyMedium.copy(color = secondaryOnBackGround),
                modifier = modifier.padding(start = 16.dp)
            )
            SearchBar(
                searchText = searchText,
                onSearchTextChange = { newValue ->
                    searchText = newValue
                    displayedProducts = if (newValue.isBlank()) {
                        displayedProducts
                    } else {
                        searchPrefix(newValue)
                    }
                }, modifier = modifier.padding(start = 16.dp, end = 16.dp,top = 32.dp)
            )
            CategoriesSection(onCategorySelected = onCategorySelected, modifier = modifier.padding(start = 8.dp,top = 16.dp))
            Text(
                "New Arrival", style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.padding(start = 16.dp, top = 45.dp)
            )
        }

        Box(modifier = modifier.fillMaxSize()) {
            when (state) {
                is ScreenState.Error -> {
                    ProductsLazyVerticalGrid(
                        products = state.data ?: emptyList(),
                        onProductClick = onProductClick,
                        onCartButtonClicked = onCartButtonClicked,
                        isConnected = isConnected,

                        )
                    Log.i("Store Screen", state.message ?: "An unexpected error occurred")
                }

                is ScreenState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is ScreenState.Success -> {
                    ProductsLazyVerticalGrid(
                        products = displayedProducts,
                        onProductClick = onProductClick,
                        onCartButtonClicked = onCartButtonClicked,
                        isConnected = isConnected,
                    )
                }
            }
            if (showRefreshButton) {
                IconButton(
                    onClick = {
                        getAllProducts()
                        showRefreshButton = false
                    },
                    modifier = modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .align(Alignment.TopCenter),
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                }
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
    var showSnackBar by remember { mutableStateOf(true) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item(span = { GridItemSpan(2) }) {
            if (showSnackBar && !isConnected) {
                SnackBarManager.showMessage(AppText.offline_message)
                showSnackBar = false
            }
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
                id = 0,
                image = ""
            )

        )
        val state: ScreenState<List<ProductModel>> = ScreenState.Success(productsList)
        StoreContent(state = state, {}, {}, {}, searchPrefix = { emptyList() }, {})
    }
}