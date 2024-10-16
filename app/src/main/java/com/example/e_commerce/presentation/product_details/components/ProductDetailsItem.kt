package com.example.e_commerce.presentation.product_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerce.common.composable.CommerceToolBar
import com.example.e_commerce.common.composable.CommerceWideButtonWithData
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.ui.theme.secondaryOnBackGround
import com.example.e_commerce.R.string as AppText
import com.example.e_commerce.R.drawable as AppIcon

@Composable
fun ProductDetailsItem(
    product: ProductModel,
    onNavigationBackClicked: () -> Unit,
    onCartButtonClicked: (ProductModel) -> Unit, modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom =80.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CommerceToolBar(
                title = AppText.product_details_top_bar,
                navigationIcon = AppIcon.back,
                onNavigationBack = onNavigationBackClicked
            )
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = modifier
                    .fillMaxWidth()
                    .height(418.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = modifier.height(8.dp))
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp, end = 16.dp
                    )
            ) {
                Row(
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.labelSmall.copy(color = secondaryOnBackGround),
                        modifier = modifier
                            .padding(start = 8.dp, end = 10.dp, bottom = 4.dp)
                            .weight(1f)
                    )
                    Text(
                        text = "Price",
                        style = MaterialTheme.typography.labelSmall.copy(color = secondaryOnBackGround),
                        modifier = modifier.padding(end = 16.dp)
                    )
                }
                Row(
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = product.category,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier
                            .padding(start = 8.dp)
                            .weight(1f)
                    )
                    Text(
                        text = "$${product.price}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier.padding(end = 8.dp)
                    )
                }
            }
            Spacer(modifier = modifier.height(16.dp))
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    "Description",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = modifier.padding(start = 8.dp, bottom = 4.dp)
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium.copy(color = secondaryOnBackGround),
                    modifier = modifier.padding(start = 8.dp, end = 10.dp, bottom =8.dp)
                )
            }
        }
        CommerceWideButtonWithData(
            text = AppText.add_to_cart_button,
            modifier = modifier.align(Alignment.BottomCenter),
            data = product,
            action = {
                onCartButtonClicked(product)
            })
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
        ProductDetailsItem(product, {}, {})
    }
}