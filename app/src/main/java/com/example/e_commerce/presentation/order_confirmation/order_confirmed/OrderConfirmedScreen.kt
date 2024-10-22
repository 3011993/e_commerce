package com.example.e_commerce.presentation.order_confirmation.order_confirmed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.common.composable.CommerceWideButton
import com.example.e_commerce.presentation.order_confirmation.OrderConfirmationViewModel
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.ui.theme.secondaryOnBackGround
import com.example.e_commerce.R.string as AppText
import com.example.e_commerce.R.drawable as AppIcon

@Composable
fun OrderConfirmedScreen(openScreen: (String) -> Unit,modifier: Modifier = Modifier) {
    val viewModel : OrderConfirmationViewModel = hiltViewModel()
    OrderConfirmedContent(onContinueShoppingClicked = {viewModel.onContinueShoppingClicked(openScreen)},modifier)
}

@Composable
fun OrderConfirmedContent(onContinueShoppingClicked : () -> Unit,modifier: Modifier = Modifier,) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(top = 180.dp)
        ) {
            Image(
                painter = painterResource(AppIcon.order_confirmed_image),
                contentDescription = null,
                modifier
                    .width(279.dp)
                    .height(232.dp)
            )
            Text("Order Confirmed!", style = MaterialTheme.typography.labelLarge)
            Text(
                text = stringResource(AppText.secondary_order_confirmed_text),
                style = MaterialTheme.typography.labelSmall.copy(color = secondaryOnBackGround),
                modifier = Modifier
                    .width(322.dp)
                    .height(42.dp),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        CommerceWideButton(AppText.continue_shopping_button, action = onContinueShoppingClicked)
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderConfirmedContentPreview() {
    E_commerceTheme {
        OrderConfirmedContent(onContinueShoppingClicked = {})
    }
}