package com.example.e_commerce.presentation.store.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerce.R
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
            .width(160.dp)
            .height(257.dp)
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clickable {
                    onProductClicked(product)
                }
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {

                AsyncImage(
                    model = product.image,
                    contentDescription = product.title,
                    modifier = modifier
                        .width(160.dp)
                        .height(203.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = modifier.height(2.dp))
                val words = product.title.split(" ")
                val firstTwoWords =
                    if (words.size >= 4) words.subList(0, 4).joinToString(" ") else product.title
                Text(
                    text = firstTwoWords,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = modifier
                        .height(15.dp)
                        .width(117.dp)
                        .offset(3.dp)
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.height(14.dp).width(26.dp).offset(x = 3.dp)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.heart),
                contentDescription = null,
                modifier = modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp, top = 8.dp)
            )
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null,
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .clickable { onCartButtonClicked(product) }
                    .padding(end = 8.dp, bottom = 8.dp)
            )
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
        ProductItem(product, {}, {})
    }

}