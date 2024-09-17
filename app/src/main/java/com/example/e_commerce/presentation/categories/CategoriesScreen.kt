package com.example.e_commerce.presentation.categories

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.presentation.categories.components.CategoriesNavigationRail
import com.example.e_commerce.presentation.store.StoreContent
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(3.dp)) {
        val viewModel: CategoriesViewModel = hiltViewModel()
        val state by viewModel.allProducts.collectAsState()
        val categories by viewModel.categories.collectAsState()
        CategoriesNavigationRail(
            onSelectCategory = { category ->
                viewModel.getProductsByCategory(category.category)
            },
            categories
        )
        //StoreContent(state = state, {},{},{}, searchPrefix = { (null) })
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    E_commerceTheme {
        CategoriesScreen()
    }
}