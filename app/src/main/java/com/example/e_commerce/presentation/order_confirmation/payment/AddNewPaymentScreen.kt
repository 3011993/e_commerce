package com.example.e_commerce.presentation.order_confirmation.payment

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.common.composable.CommerceToolBar
import com.example.e_commerce.common.composable.CommerceWideButton
import com.example.e_commerce.common.composable.PaymentField
import com.example.e_commerce.common.composable.SmallPaymentField
import com.example.e_commerce.presentation.order_confirmation.OrderConfirmationViewModel
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.R.string as AppText
import com.example.e_commerce.R.drawable as AppIcon

@Composable
fun AddNewPaymentScreen(openScreen : (String) -> Unit,onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
    val viewModel: OrderConfirmationViewModel = hiltViewModel()
    val paymentUiState by viewModel.paymentUiState
    AddNewPaymentContent(
        uiState = paymentUiState,
        onCardOwnerChange = viewModel::onCardOwnerChange,
        onCardNumberChange = viewModel::onCardNumberChange,
        onExpChange = viewModel::onExpChange,
        onCvvChange = viewModel::onCvvChange,
        onNavigateBack = onNavigateBack,
        onSavePaymentClicked = {viewModel.onSavePaymentClicked(openScreen)},
        modifier = modifier
    )
}

@Composable
fun AddNewPaymentContent(
    uiState: PaymentUiState,
    onCardOwnerChange: (String) -> Unit,
    onCardNumberChange: (String) -> Unit,
    onExpChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onSavePaymentClicked : () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CommerceToolBar(
            onNavigationBack = onNavigateBack,
            title = AppText.add_new_payment_bar,
            modifier = modifier,
            navigationIcon = AppIcon.back
        )
        PaymentMethodsSection()
        Text(
            stringResource(AppText.card_owner),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        )
        PaymentField(
            text = AppText.card_owner,
            value = uiState.cardOwner,
            onNewValue = onCardOwnerChange,
            placeholder = AppText.card_owner
        )
        Text(
            stringResource(AppText.card_number),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        )
        PaymentField(
            text = AppText.card_owner,
            value = uiState.cardNumber,
            keyboardType = KeyboardType.Number,
            onNewValue = onCardNumberChange,
            placeholder = AppText.card_owner
        )
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Column {
                Text(
                    stringResource(AppText.exp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp)
                )
                SmallPaymentField(
                    value = uiState.exp, onNewValue = onExpChange,
                    keyboardType = KeyboardType.Number, placeholder = AppText.exp
                )
            }
            Column {
                Text(
                    stringResource(AppText.cvv),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp)
                )
                SmallPaymentField(
                    value = uiState.cvv,
                    onNewValue = onCvvChange,
                    keyboardType = KeyboardType.Number,
                    placeholder = AppText.cvv
                )
            }
        }
        Spacer(modifier.weight(1f))
        CommerceWideButton(text = AppText.add_new_card_button, action = onSavePaymentClicked)
    }
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
//        AddNewPaymentContent("",{},{})
    }

}