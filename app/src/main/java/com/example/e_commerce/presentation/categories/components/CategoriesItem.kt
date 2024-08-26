package com.example.e_commerce.presentation.categories.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce.presentation.categories.ProductCategory
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CategoriesNavigationRail(
    onSelectCategory: (ProductCategory) -> Unit,
    categories: List<ProductCategory>,
    modifier: Modifier = Modifier,
) {
    var selectedCategory by remember {
        mutableStateOf(categories.firstOrNull())
    }
    NavigationRail(
        windowInsets = WindowInsets.systemBars,
        modifier = modifier.padding(6.dp)
    ) {
        categories.forEach { category ->
            NavigationRailItem(
                selected = selectedCategory == category,
                onClick = {
                    selectedCategory = category
                    onSelectCategory(category)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Create, contentDescription = null,
                        modifier = modifier.size(0.dp)
                    )
                },
                label = {
                    Text(
                        text = category.value,
                        modifier = modifier
                            .align(Alignment.Start)
                    )
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoriesNavigationRailPreview() {
    E_commerceTheme {
        CategoriesNavigationRail({}, ProductCategory.entries)
    }
}