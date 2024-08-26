package com.example.e_commerce.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.lifecycle.ViewModel
import com.example.e_commerce.presentation.ScreenState
import com.example.e_commerce.presentation.categories.components.CategoriesNavigationRail
import com.example.e_commerce.presentation.store.StoreContent
import com.example.e_commerce.presentation.store.components.ProductItem
import com.example.e_commerce.ui.theme.E_commerceTheme


@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(3.dp)) {
        val viewModel: CategoriesViewModel = hiltViewModel()
        val state by viewModel.allProducts.collectAsState()
        CategoriesNavigationRail(
            onSelectCategory = { category ->
                viewModel.getProductsByCategory(category)
            },
            ProductCategory.entries
        )
        StoreContent(state = state)
    }

}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    E_commerceTheme {
        CategoriesScreen()
    }
}