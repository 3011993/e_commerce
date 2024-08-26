package com.example.e_commerce.presentation.cart.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun CartItem(
    cartModel: CartModel,
    productModel: ProductModel,
    onIncreaseQuantity: (CartModel) -> Unit,
    onDecreaseQuantity: (CartModel) -> Unit,
    onRemoveItem: (CartModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically // Align items vertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) { // Center image horizontally
            AsyncImage(
                model = productModel.image,
                contentDescription = productModel.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)))
            Spacer(modifier = Modifier.height(8.dp)) // Add space below image
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onDecreaseQuantity(cartModel) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown, contentDescription = "Decrease Quantity"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp)) // Space between buttons and quantity
                Text(text = "3") // Replace with actual quantity from cartModel
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { onIncreaseQuantity(cartModel) }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Increase Quantity")
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f)) // Push image and buttons to the left
        Column { // Details on the right
            Text(text = productModel.title, fontWeight = FontWeight.Bold)
            Text(text = "$${productModel.price}")
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(onClick = {onRemoveItem(cartModel) }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Remove Item")
            }
        }
    }
}
//@Composable
//fun CartItem(
//    cartModel: CartModel,
//    productModel: ProductModel,
//    onIncreaseQuantity: (CartModel) -> Unit,
//    onDecreaseQuantity: (CartModel) -> Unit,
//    onRemoveItem: (CartModel) -> Unit,
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        AsyncImage(
//            model = productModel.image,
//            contentDescription = productModel.title,
//            modifier = Modifier
//                .size(80.dp)
//                .clip(RoundedCornerShape(8.dp))
//        )
//        Spacer(modifier = Modifier.width(16.dp))
//        Column(modifier = Modifier.weight(1f)) {
//            Text(text = productModel.title, fontWeight = FontWeight.Bold)
//            Text(text = "$${productModel.price}")
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                IconButton(onClick = { onDecreaseQuantity(cartModel) }) {
//                    Icon(
//                        imageVector = Icons.Default.Refresh, contentDescription = "Decrease Quantity"
//                    )
//                }
//                Text(text = "3")
//                IconButton(onClick = { onIncreaseQuantity(cartModel) }) {
//                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Increase Quantity")
//                }
//                Spacer(modifier = Modifier.weight(1f)) // Push buttons to the left
//                IconButton(onClick = { onRemoveItem(cartModel) }) {
//                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Remove Item")
//                }
//            }
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    E_commerceTheme {
        val cart = CartModel(
            date = "", id = 0, productCarts = emptyList(), userId = 0
        )
        val product = ProductModel(
            category = "Electronics",
            price = "140 EGP",
            image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            description = "this is ay 7age",
            title = "Bag",
            id = 0
        )
        CartItem(productModel = product,
            cartModel = cart,
            onIncreaseQuantity = {},
            onDecreaseQuantity = {}) {

        }
    }
}