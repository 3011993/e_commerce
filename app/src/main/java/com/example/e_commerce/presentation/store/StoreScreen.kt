package com.example.e_commerce.presentation.store

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.home.HomeContent
import com.example.e_commerce.presentation.home.HomeViewModel

@Composable
fun StoreScreen(
    onProductClick: (ProductModel) -> Unit, onCartButtonClicked: (ProductModel) -> Unit,
    onStoreClicked : () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.allProducts.collectAsState()
    val productsInStock by viewModel.inStock.collectAsState()
    HomeContent(
        state = state,
        onProductClick = onProductClick,
        onCartButtonClicked = onCartButtonClicked,
        getAllProducts = viewModel::getAllProducts,
        searchPrefix = viewModel::searchProducts,
        onCategorySelected = viewModel::getProductsByCategory,
        onFavouriteClicked = viewModel::onFavouriteClicked,
        productsInStock = productsInStock,
        onStoreClicked = onStoreClicked,
        modifier = modifier
    )
}
