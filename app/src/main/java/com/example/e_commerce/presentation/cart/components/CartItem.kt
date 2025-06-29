package com.example.e_commerce.presentation.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerce.common.ext.adjustPrice
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.ui.theme.secondaryOnBackGround
import com.example.e_commerce.R.drawable as AppIcon

@Composable
fun CartItem(
    cartItem: CartModel,
    onIncreaseQuantity: (CartModel) -> Unit,
    onDecreaseQuantity: (CartModel) -> Unit,
    onRemoveItem: (CartModel) -> Unit, modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(
                start = 16.dp, end = 16.dp,
                top = 8.dp
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
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(top = 8.dp, start = 8.dp)
                    .weight(1f)
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(2f)
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
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onDecreaseQuantity(cartItem) },
                        modifier = modifier.clip(CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcon.decrease_button),
                            contentDescription = "Decrease quantity"
                        )
                    }
                    Text(
                        text = "${cartItem.quantity}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    IconButton(
                        onClick = {
                            onIncreaseQuantity(cartItem)
                        },
                        modifier = modifier.clip(CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(AppIcon.increase_button),
                            contentDescription = "Increase quantity"
                        )
                    }
                    IconButton(
                        onClick = { onRemoveItem(cartItem) },
                        modifier = modifier.clip(CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(AppIcon.delete_button),
                            contentDescription = "Remove Item"
                        )
                    }
                }

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    E_commerceTheme {

        val cartItemModel = CartModel(
            price = 99.0,
            quantity = 2,
            productId = 1,
            title = "Bag"
        )
        CartItem(
            cartItem = cartItemModel,
            onIncreaseQuantity = {},
            onDecreaseQuantity = {},
            onRemoveItem = {}
        )

    }
}