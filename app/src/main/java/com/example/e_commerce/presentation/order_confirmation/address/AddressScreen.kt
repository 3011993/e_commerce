package com.example.e_commerce.presentation.order_confirmation.address

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.example.e_commerce.domain.model.AddressModel
import com.example.e_commerce.presentation.order_confirmation.OrderConfirmationViewModel
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.R.string as AppText
import com.example.e_commerce.R.drawable as AppIcon


@Composable
fun AddressScreen(openScreen:(String) -> Unit,onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
    val viewModel: OrderConfirmationViewModel = hiltViewModel()
    val addressUiState by viewModel.addressModel
    AddressContent(
        uiState = addressUiState,
        onNameChange = viewModel::onNameChange,
        onCountryChange = viewModel::onCountryChange,
        onCityChange = viewModel::onCityChange,
        onAddressChange = viewModel::onAddressChange,
        onPhoneNumberChange = viewModel::onPhoneNumberChange,
        onSaveAddressClicked = {viewModel.onSaveAddressClicked(openScreen)},
        onNavigateBack = onNavigateBack,
        modifier = modifier
    )
}

@Composable
fun AddressContent(
    uiState: AddressModel,
    onNameChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onSaveAddressClicked : () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CommerceToolBar(
            title = AppText.address_bar,
            navigationIcon = AppIcon.back,
            onNavigationBack = onNavigateBack
        )
        Text(
            stringResource(AppText.name),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        )
        PaymentField(
            text = AppText.name,
            value = uiState.name,
            onNewValue = onNameChange,
            AppText.name
        )
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Column {
                Text(
                    stringResource(AppText.country),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp)
                )
                SmallPaymentField(
                    value = uiState.country,
                    onNewValue = onCountryChange,
                    placeholder = AppText.country
                )
            }
            Column {
                Text(
                    stringResource(AppText.city),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp)
                )
                SmallPaymentField(
                    value = uiState.city,
                    onNewValue = onCityChange,
                    placeholder = AppText.city
                )
            }
        }
        Text(
            stringResource(AppText.phone_number),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        )
        PaymentField(
            text = AppText.phone_number,
            value = uiState.phoneNumber,
            onNewValue = onPhoneNumberChange,
            keyboardType = KeyboardType.Number,
            placeholder = AppText.phone_number
        )
        Text(
            "Address", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            modifier = modifier.padding(start = 16.dp, end = 16.dp)
        )
        PaymentField(
            text = AppText.address,
            value = uiState.address,
            onNewValue = onAddressChange,
            placeholder = AppText.address
        )
        Spacer(modifier.weight(1f))
        CommerceWideButton(text = AppText.save_address_card_button, action = onSaveAddressClicked)

    }

}

@Preview(showBackground = true)
@Composable
fun AddressContentPreview(modifier: Modifier = Modifier) {
    E_commerceTheme {
//        AddressContent({})
    }
}