package com.example.e_commerce.presentation.wishlist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.common.composable.CommerceToolBar
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.ScreenState
import com.example.e_commerce.presentation.wishlist.components.WishListAvailableItems
import com.example.e_commerce.presentation.home.ProductsLazyVerticalGrid
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.R.string as AppText
import com.example.e_commerce.R.drawable as AppIcon

@Composable
fun WishlistScreen(clearAndNavigate: (String) -> Unit, modifier: Modifier = Modifier) {
    val viewModel: WishListViewModel = hiltViewModel()
    val state by viewModel.allProducts.collectAsState()
    val isInStock by viewModel.inStock.collectAsState()

    WishListContent(
        state = state,
        onProductClick = {},
        onCartButtonClicked = viewModel::addOrUpdateCart,
        onFavouriteButtonClicked = viewModel::onFavouriteClicked,
        isConnected = true,
        onNavigationBackClicked = { viewModel.onNavigateBackClicked(clearAndNavigate) },
        isInStock = isInStock,
        modifier = modifier
    )
}

@Composable
fun WishListContent(
    state: ScreenState<List<ProductModel>>,
    onProductClick: (ProductModel) -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit,
    onFavouriteButtonClicked: (ProductModel) -> Unit,
    onNavigationBackClicked: () -> Unit,
    isInStock: Map<String,Boolean>,
    isConnected: Boolean, modifier: Modifier = Modifier,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CommerceToolBar(
            title = AppText.wishlist_top_bar,
            navigationIcon = AppIcon.back,
            onNavigationBackClicked = onNavigationBackClicked,
            modifier = Modifier
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
                            productsInStock = isInStock,
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
                            productsInStock = isInStock,
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
        WishListContent(state, {}, {}, {}, isConnected = true, onNavigationBackClicked = {},
            isInStock = emptyMap()
        )
    }
}