package com.example.e_commerce.presentation.wishlist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.ui.theme.secondaryOnBackGround

@Composable
fun WishListAvailableItems(count : Int,modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().padding(8.dp)) {
        Text("$count Items", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium))
        Text("in wishlist", style = MaterialTheme.typography.labelSmall.copy(color = secondaryOnBackGround, fontSize = 15.sp))
    }
}

@Preview(showBackground = true)
@Composable
private fun WishListAvailableItemsPreview() {
    E_commerceTheme {
        WishListAvailableItems(5)
    }
}