package com.example.e_commerce.presentation.order_confirmation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.e_commerce.common.composable.CommerceToolBar
import com.example.e_commerce.common.composable.CommerceWideButton
import com.example.e_commerce.common.ext.adjustPrice
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.ui.theme.secondaryOnBackGround
import com.example.e_commerce.R.string as AppText
import com.example.e_commerce.R.drawable as AppIcon


@Composable
fun OrderConfirmationScreen(
    openAddressScreen: (String) -> Unit,
    openPaymentScreen: (String) -> Unit,
    onNavigationBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: OrderConfirmationViewModel = hiltViewModel()
    val carts by viewModel.carts.collectAsState()
    OrderConfirmationContent(
        carts = carts, onAddressClicked = { viewModel.onAddressClicked(openAddressScreen) },
        onPaymentClicked = { viewModel.onPaymentClicked(openPaymentScreen) },
        onNavigationBack = onNavigationBack, modifier = modifier
    )
}

@Composable
fun OrderConfirmationContent(
    carts: List<CartModel>, onAddressClicked: () -> Unit, onPaymentClicked: () -> Unit,
    onNavigationBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CommerceToolBar(
            title = AppText.order_confirmation_bar,
            navigationIcon = AppIcon.back,
            onNavigationBack = onNavigationBack
        )
        OrderSummarySection(carts)
        AddressSection(onAddressClicked)
        PaymentSection(onPaymentClicked)
        Spacer(modifier.weight(1f))
        CommerceWideButton(AppText.place_order_button, action = {})
    }
}

@Composable
fun OrderSummarySection(cartItems: List<CartModel>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Order Summary:", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp))
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(cartItems) { cartItem ->
                OrderItem(cartItem)
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val subTotal = cartItems.sumOf { it.price }
            Text(
                "SubToTal",
                style = MaterialTheme.typography.bodyMedium.copy(color = secondaryOnBackGround)
            )
            Text(subTotal.adjustPrice(), style = MaterialTheme.typography.bodyMedium)
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Shipping",
                style = MaterialTheme.typography.bodyMedium.copy(color = secondaryOnBackGround)
            )
            Text("5 $", style = MaterialTheme.typography.bodyMedium)
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val total = cartItems.sumOf { it.price } + 5
            Text(
                "Total",
                style = MaterialTheme.typography.bodyMedium.copy(color = secondaryOnBackGround)
            )
            Text(total.adjustPrice(), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun OrderItem(
    cartItem: CartModel,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(110.dp)
            .fillMaxWidth()
            .padding(
                start = 16.dp, end = 16.dp,
                top = 8.dp, bottom = 8.dp
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            AsyncImage(
                model = cartItem.image,
                contentDescription = cartItem.title,
                modifier = Modifier
                    .width(100.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(top = 8.dp, start = 8.dp)
                    .weight(1f)
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    cartItem.title,
                    modifier = modifier.padding(start = 8.dp, top = 8.dp),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                    fontSize = 13.sp
                )
                Text(
                    cartItem.price.adjustPrice(),
                    style = MaterialTheme.typography.titleSmall.copy(color = secondaryOnBackGround),
                    modifier = modifier.padding(start = 8.dp, top = 2.dp),
                )
                Text(
                    text = "${cartItem.quantity}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

    }
}

@Composable
fun AddressSection(onAddressClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Delivery Address:",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp)
            )
            IconButton(onClick = onAddressClicked) {
                Icon(painter = painterResource(AppIcon.arrow_icon), contentDescription = null)
            }
        }
        Text(
            "31 Mohmaed Street",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 15.sp)
        )
        Text(
            "Giza",
            style = MaterialTheme.typography.labelSmall.copy(color = secondaryOnBackGround)
        )
    }
}

@Composable
fun PaymentSection(onPaymentClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Payment Method:",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp)
            )
            IconButton(onClick = onPaymentClicked) {
                Icon(painter = painterResource(AppIcon.arrow_icon), contentDescription = null)
            }
        }
        Text(
            "Card Details",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 15.sp)
        )
        Text(
            "**** 6777",
            style = MaterialTheme.typography.labelSmall.copy(color = secondaryOnBackGround)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun OrderConfirmationPreview() {
    E_commerceTheme {
        val cartItems = listOf(
            CartModel(title = "bag", price = 100.0, productId = 1, quantity = 2),
            CartModel(title = "sanDisk", price = 200.0, productId = 1, quantity = 1)
        )
        OrderConfirmationContent(cartItems, {}, {},{})
    }
}