package com.example.e_commerce.presentation.product_details


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.ScreenState
import com.example.e_commerce.presentation.store.components.ProductItem

@Composable
fun ProductDetailsScreen(modifier: Modifier = Modifier) {
    val viewModel: ProductDetailsViewModel = hiltViewModel()
    val state by viewModel.product.collectAsState()
    ProductDetailsContent(state = state, modifier = modifier)
}

@Composable
fun ProductDetailsContent(state: ScreenState<ProductModel>, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is ScreenState.Error -> {
                Text(
                    text = state.message ?: "An unexpected error occurred",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }

            is ScreenState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ScreenState.Success -> {
                ProductItem(product = state.data,{})
            }
        }
    }
}