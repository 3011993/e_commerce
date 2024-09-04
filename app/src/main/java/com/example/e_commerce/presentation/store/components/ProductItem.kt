package com.example.e_commerce.presentation.store.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun ProductItem(
    product: ProductModel,
    onProductClicked: (ProductModel) -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .clickable {
                    onProductClicked(product)
                }
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = modifier.height(8.dp))
            val words = product.title.split(" ")
            val firstTwoWords =
                if (words.size >= 2) words.subList(0, 2).joinToString(" ") else product.title
            Text(
                text = firstTwoWords,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,

                )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.subtitle1,
                color = Color.Gray
            )
            Text(
                text = "Category: ${product.category}",
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = modifier.height(4.dp))
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null,
                modifier = modifier.clickable { onCartButtonClicked(product) })
        }
    }
}

@Preview
@Composable
private fun ProductItemPreview() {
    E_commerceTheme {
        val product = ProductModel(
            category = "Electronics",
            price = "140 EGP",
            image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            description = "this is ay 7age",
            title = "Bag",
            id = 0
        )
        ProductItem(product, {},{})
    }

}