package com.example.e_commerce.presentation.cart.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_commerce.common.composable.CommerceToolBar
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.R.string as AppText
import com.example.e_commerce.R.drawable as AppIcon

@Composable
fun CartHeader(modifier: Modifier = Modifier) {
    CommerceToolBar(
        title = AppText.carts_top_bar,
        navigationIcon = AppIcon.back,
        onNavigationClickedBack = {},
        modifier = modifier
    )
}

@Preview
@Composable
private fun CartHeaderPreview() {
    E_commerceTheme {
        CartHeader()
    }
}