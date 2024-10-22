package com.example.e_commerce.presentation.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce.R
import com.example.e_commerce.common.composable.CommerceWideButton
import com.example.e_commerce.common.composable.DialogCancelButton
import com.example.e_commerce.common.composable.DialogConfirmButton
import com.example.e_commerce.common.ext.adjustPrice
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.ui.theme.secondaryOnBackGround
import com.example.e_commerce.R.string as AppText

@Composable
fun CheckOutBottom(
    totalPrice: Double, onCheckOutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .shadow(elevation = 4.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total Price",
                    style = MaterialTheme.typography.bodyMedium.copy(color = secondaryOnBackGround)
                )
                Text(totalPrice.adjustPrice(), style = MaterialTheme.typography.bodyMedium)
            }
            CommerceWideButton(
                text = AppText.check_out_button,
                modifier = modifier,
                action = onCheckOutClick
            )
        }
    }
}
@Composable
fun CheckOutDialog(openScreen :()-> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }
    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.sign_in_title)) },
            text = { Text(stringResource(R.string.sign_in_description)) },
            dismissButton = { DialogCancelButton(R.string.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(R.string.sign_in) {
                    openScreen()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }

}

@Preview
@Composable
private fun CheckOutBottomPreview() {
    E_commerceTheme {
        CheckOutBottom(19.0, {})
    }
}