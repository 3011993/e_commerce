package com.example.e_commerce.presentation.order_confirmation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun AddressScreen(onNavigateBack: () -> Unit,modifier: Modifier = Modifier) {
    AddressContent(onNavigateBack = onNavigateBack, modifier)
}
@Composable
fun AddressContent(onNavigateBack : () -> Unit,modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)){
        CommerceToolBar(title = AppText.address_bar, navigationIcon = AppIcon.back, onNavigationBack = onNavigateBack)
        Text(
            "Name", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        )
        PaymentField(text = AppText.card_owner, value = "Ahmed Mosad", onNewValue = {})
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Column {
                Text("Country", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp))
                SmallPaymentField(value = "Egypt", onNewValue = {})
            }
            Column {
                Text("City", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp))
                SmallPaymentField(value = "Giza", onNewValue = {})
            }
        }
        Text(
            "Phone Number", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        )
        PaymentField(text = AppText.card_owner, value = "+201200725528", onNewValue = {})
        Text(
            "Address", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        )
        PaymentField(text = AppText.card_owner, value = "31 Mohamed Abdo street", onNewValue = {})
        Spacer(modifier.weight(1f))
        CommerceWideButton(text = AppText.save_address_card_button, action = {})

    }

}
@Preview(showBackground = true)
@Composable
fun AddressContentPreview(modifier: Modifier = Modifier) {
    E_commerceTheme {
        AddressContent({})
    }
}