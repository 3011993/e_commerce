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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.presentation.ScreenState
import com.example.e_commerce.presentation.product_details.components.ProductDetailsItem
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun ProductDetailsScreen(
    onNavigationBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: ProductDetailsViewModel = hiltViewModel()
    val state by viewModel.product.collectAsState()
    ProductDetailsContent(
        state = state,
        onCartButtonClicked = viewModel::addOrUpdateCart,
        onNavigationBackClicked = onNavigationBackClicked,
        modifier = modifier
    )
}

@Composable
fun ProductDetailsContent(
    state: ScreenState<ProductModel>,
    onCartButtonClicked: (ProductModel) -> Unit,
    onNavigationBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
                ProductDetailsItem(
                    product = state.data, onCartButtonClicked = onCartButtonClicked,
                    onNavigationBackClicked = onNavigationBackClicked
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailsContentPreview() {
    E_commerceTheme {
        val productModel = ProductModel(title = "Bag", price = "88.00", category = "clothing", id = 0, image = "", description = "ay 7aga")
        val state = ScreenState.Success(productModel)
        ProductDetailsContent(state,{},{})
    }

}