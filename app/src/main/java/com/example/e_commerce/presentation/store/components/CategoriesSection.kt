package com.example.e_commerce.presentation.store.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce.presentation.store.CategoriesEntries
import com.example.e_commerce.presentation.store.ecommerceCategories
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CategoriesSection(
    onCategorySelected: (CategoriesEntries) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedCategory by remember { mutableStateOf<CategoriesEntries?>(null) }
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Choose Category",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(start = 8.dp)
        )
        LazyRow(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(end = 8.dp)
        ) {
            items(ecommerceCategories) { category ->
                CategoryChip(category = category.category, icon = category.icon,
                    selected = category == selectedCategory,
                    onCategorySelected = {
                        selectedCategory = category
                        onCategorySelected(category)
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryChip(
    category: String,
    selected: Boolean,
    onCategorySelected: () -> Unit,
    icon: Int,
    modifier: Modifier = Modifier,
) {
    Chip(
        onClick = { onCategorySelected() },
        leadingIcon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = category,
                modifier = modifier.size(40.dp)
            )
        }, colors = ChipDefaults.chipColors(
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(8.dp),
        border = if (selected) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null
    ) { Text(category.toUpperCase(Locale.current), style = MaterialTheme.typography.bodyMedium) }
}

@Preview(showBackground = true)
@Composable
private fun CategoriesSectionPreview() {
    E_commerceTheme {
        CategoriesSection({})
    }
}