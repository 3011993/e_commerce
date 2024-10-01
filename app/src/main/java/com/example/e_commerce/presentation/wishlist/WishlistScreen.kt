package com.example.e_commerce.presentation.wishlist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.ScreenState
import com.example.e_commerce.presentation.wishlist.components.WishListAvailableItems
import com.example.e_commerce.presentation.home.ProductsLazyVerticalGrid
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun WishlistScreen(modifier: Modifier = Modifier) {
    val viewModel: WishListViewModel = hiltViewModel()
    val state by viewModel.allProducts.collectAsState()
    WishListContent(
        state = state,
        onProductClick = {},
        onCartButtonClicked = {},
        onFavouriteButtonClicked = viewModel::onFavouriteClicked,
        isConnected = true,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishListContent(
    state: ScreenState<List<ProductModel>>,
    onProductClick: (ProductModel) -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit,
    onFavouriteButtonClicked : (ProductModel) -> Unit,
    isConnected: Boolean, modifier: Modifier = Modifier,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    "Wishlist",
                    modifier = modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = androidx.compose.material.MaterialTheme.colors.background
            )
        )
        Box(modifier = modifier.fillMaxSize()) {
            when (state) {
                is ScreenState.Error -> {
                    Card(
                        modifier = modifier.fillMaxSize(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        WishListAvailableItems(state.data?.size ?: 0)
                        ProductsLazyVerticalGrid(
                            products = state.data ?: emptyList(),
                            onProductClick = onProductClick,
                            onCartButtonClicked = onCartButtonClicked,
                            isConnected = isConnected,
                            onFavouriteClicked = {},
                        )
                        Log.i("Store Screen", state.message ?: "An unexpected error occurred")
                    }
                }

                is ScreenState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is ScreenState.Success -> {
                    Card(
                        modifier = modifier.fillMaxSize(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {
                        WishListAvailableItems(state.data.size)
                        ProductsLazyVerticalGrid(
                            products = state.data,
                            onProductClick = onProductClick,
                            onCartButtonClicked = onCartButtonClicked,
                            isConnected = isConnected,
                            onFavouriteClicked = onFavouriteButtonClicked,
                            modifier = modifier
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    E_commerceTheme {
        val products = listOf(
            ProductModel(
                title = "Bag",
                price = "88.44",
                category = "",
                description = "",
                id = 0,
                image = ""
            ),
            ProductModel(
                title = "Bag",
                price = "88.44",
                category = "",
                description = "",
                id = 0,
                image = ""
            ),
            ProductModel(
                title = "Bag",
                price = "88.44",
                category = "",
                description = "",
                id = 0,
                image = ""
            ),
            ProductModel(
                title = "Bag",
                price = "88.44",
                category = "",
                description = "",
                id = 0,
                image = ""
            ),
        )
        val state = ScreenState.Success(products)
        WishListContent(state, {}, {}, {}, isConnected = true)
    }
}