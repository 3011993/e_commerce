package com.example.e_commerce.presentation.product_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun ProductDetailsItem(
    product: ProductModel,
    onCartButtonClicked: (ProductModel) -> Unit, modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp))
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
        Text(
            text = product.title,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(start = 8.dp, end = 10.dp)

        )
        Text(
            text = "$${product.price}",
            style = MaterialTheme.typography.subtitle1,
            color = Color.Gray,
            modifier = modifier.padding(start = 8.dp)

        )
        Text(
            text = "Category: ${product.category}",
            style = MaterialTheme.typography.body2,
            modifier = modifier.padding(start = 8.dp)
        )
        Text(
            text = "Description: ${product.description}",
            style = MaterialTheme.typography.body2,
            modifier = modifier.padding(start = 8.dp, end = 10.dp)
        )
        Spacer(modifier = modifier.height(4.dp))
        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null,
            modifier = modifier
                .align(Alignment.End)
                .padding(end = 10.dp, bottom = 16.dp)
                .clickable { onCartButtonClicked(product) })
    }

}

@Preview(showBackground = true)
@Composable
private fun ProductDetailsItemPreview() {
    E_commerceTheme {
        val product = ProductModel(
            category = "Electronics",
            price = "140 EGP",
            image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            description = "this is ay 7age",
            title = "Bag",
            id = 0
        )
        ProductDetailsItem(product, {})
    }
}