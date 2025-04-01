package com.example.e_commerce.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.R
import com.example.e_commerce.common.ConnectionState
import com.example.e_commerce.common.composable.CommerceToolBar
import com.example.e_commerce.common.composable.rememberConnectivityState
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.ScreenState
import com.example.e_commerce.presentation.StoreProducts
import com.example.e_commerce.presentation.home.components.NewArrivalsLandingImage
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.ui.theme.secondaryOnBackGround
import com.example.e_commerce.R.string as AppText

@Composable
fun HomeScreen(
    onProductClick: (ProductModel) -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit,
    onStoreClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.allProducts.collectAsState()
    val productsInStock by viewModel.inStock.collectAsState()
    HomeContent(
        state = state,
        onProductClick = onProductClick,
        onCartButtonClicked = onCartButtonClicked,
        getAllProducts = viewModel::getAllProducts,
        onFavouriteClicked = viewModel::onFavouriteClicked,
        productsInStock = productsInStock,
        onStoreClicked = onStoreClicked,
        modifier = modifier
    )
}

@Composable
fun HomeContent(
    state: ScreenState<List<ProductModel>>,
    onProductClick: (ProductModel) -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit,
    onFavouriteClicked: (ProductModel) -> Unit,
    getAllProducts: () -> Unit,
    onStoreClicked: () -> Unit,
    productsInStock: Map<String, Boolean>,
    modifier: Modifier = Modifier,
) {
    val connection by rememberConnectivityState()
    var previousConnection by remember { mutableStateOf<ConnectionState?>(null) }
    val isConnected by remember(key1 = connection) {
        derivedStateOf { connection === ConnectionState.Available }
    }
    var showRefreshButton by remember { mutableStateOf(false) }
    var allProducts by remember { mutableStateOf<List<ProductModel>>(emptyList()) }

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
        allProducts = when (state) {
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
            CommerceToolBar(
                title = AppText.home_top_bar,
                onNavigationBack = {},
                navigationIcon = R.drawable.elkorany_app,
                modifier = Modifier
            )
            NewArrivalsLandingImage()
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "New Arrival", style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier.padding(start = 16.dp, top = 45.dp)
                )
                Text(
                    "View All",
                    style = MaterialTheme.typography.bodySmall.copy(color = secondaryOnBackGround),
                    modifier = modifier
                        .padding(end = 16.dp, top = 45.dp)
                        .clickable { onStoreClicked() },
                )
            }
        }

        Box(modifier = modifier.fillMaxSize()) {
            when (state) {
                is ScreenState.Error -> {
                    StoreProducts(
                        products = state.data ?: emptyList(),
                        onProductClick = onProductClick,
                        onCartButtonClicked = onCartButtonClicked,
                        onFavouriteClicked = onFavouriteClicked,
                        productsInStock = productsInStock,
                        isConnected = isConnected,
                    )
                    Log.i("Store Screen", state.message ?: "An unexpected error occurred")
                }

                is ScreenState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is ScreenState.Success -> {
                    StoreProducts(
                        products = state.data,
                        onProductClick = onProductClick,
                        onCartButtonClicked = onCartButtonClicked,
                        onFavouriteClicked = onFavouriteClicked,
                        productsInStock = productsInStock,
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
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.TopCenter),
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                }
            }
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
                id = 1,
                image = ""
            )

        )
        val state: ScreenState<List<ProductModel>> = ScreenState.Success(productsList)
        HomeContent(
            state = state, {}, {}, {},
            getAllProducts = {}, onStoreClicked = {}, productsInStock = emptyMap(),
        )
    }
}