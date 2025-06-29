package com.example.e_commerce.presentation.cart.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_commerce.common.composable.CommerceToolBar
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.R.drawable as AppIcon
import com.example.e_commerce.R.string as AppText

@Composable
fun CartHeader(onNavigationBackClicked: () -> Unit, modifier: Modifier = Modifier) {
    CommerceToolBar(
        title = AppText.carts_top_bar,
        navigationIcon = AppIcon.back,
        onNavigationBack = onNavigationBackClicked,
        modifier = modifier
    )
}

@Preview
@Composable
private fun CartHeaderPreview() {
    E_commerceTheme {
        CartHeader({})
    }
}