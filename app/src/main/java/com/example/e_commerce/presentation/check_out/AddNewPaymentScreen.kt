package com.example.e_commerce.presentation.check_out

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.common.composable.CommerceToolBar
import com.example.e_commerce.common.composable.CommerceWideButton
import com.example.e_commerce.common.composable.PaymentField
import com.example.e_commerce.common.composable.SmallPaymentField
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.R.string as AppText
import com.example.e_commerce.R.drawable as AppIcon

@Composable
fun AddNewPaymentScreen(modifier: Modifier = Modifier) {
    AddNewPaymentContent(modifier)
}

@Composable
fun AddNewPaymentContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        AddNewPaymentTopBar()
        PaymentMethodsSection()
        Text(
            "Card Owner", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        )
        PaymentField(text = AppText.card_owner, value = "Ahmed Mosad", onNewValue = {})
        Text(
            "Card Number", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        )
        PaymentField(text = AppText.card_owner, value = "552332 42323 5434", onNewValue = {})
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Column {
                Text("EXP", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp))
                SmallPaymentField(value = "123", onNewValue = {})
            }
            Column {
                Text("CVV", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp))
                SmallPaymentField(value = "123", onNewValue = {})
            }
        }
        Spacer(modifier.weight(1f))
        CommerceWideButton(text = AppText.add_new_card_button, action = {})
    }
}

@Composable
fun AddNewPaymentTopBar(modifier: Modifier = Modifier) {
    CommerceToolBar(
        onNavigationBackClicked = {},
        title = AppText.add_new_payment_bar,
        modifier = modifier,
        navigationIcon = AppIcon.back
    )

}

@Composable
fun PaymentMethodsSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier
                .height(50.dp)
                .width(100.dp)
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(AppIcon.master_card_icon),
                    contentDescription = null,
                    modifier = modifier.size(26.dp),
                )
            }
        }
        Card(
            modifier
                .height(50.dp)
                .width(100.dp)
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(AppIcon.paypal_icon),
                    contentDescription = null,
                    modifier = modifier.size(26.dp),
                )
            }
        }
        Card(
            modifier
                .height(50.dp)
                .width(100.dp)
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(AppIcon.bank_icon),
                    contentDescription = null,
                    modifier = modifier.size(26.dp),
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun AddNewPaymentPreview() {
    E_commerceTheme {
        AddNewPaymentContent()
    }

}