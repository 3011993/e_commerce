package com.example.e_commerce.presentation.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CartScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text("This is Cart Screen", modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    E_commerceTheme {
        CartScreen()
    }
}