package com.example.e_commerce.presentation.check_out

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_commerce.common.composable.CommerceToolBar
import com.example.e_commerce.common.composable.CommerceWideButton
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.R.string as AppText
import com.example.e_commerce.R.drawable as AppIcon



@Composable
fun CheckOutScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text("This is Check Out Screen", modifier.align(Alignment.Center))
    }
}

@Composable
fun OrderConfirmationContent(modifier: Modifier = Modifier) {
    Column(modifier= Modifier.fillMaxSize()) {
        CommerceToolBar(
            title = AppText.order_confirmation_bar,
            navigationIcon = AppIcon.back,
            onNavigationBackClicked = {})
        Spacer(modifier.weight(1f))
        CommerceWideButton(AppText.place_order_button, action = {})
    }
}

@Composable
fun OrderSummarySection(modifier: Modifier = Modifier) {
    
}

@Composable
fun AddressSection(modifier: Modifier = Modifier) {
    
}

@Composable
fun PaymentSection(modifier: Modifier = Modifier) {
    
}
@Preview(showBackground = true)
@Composable
fun OrderConfirmationPreview() {
    E_commerceTheme {
        OrderConfirmationContent()
    }
}