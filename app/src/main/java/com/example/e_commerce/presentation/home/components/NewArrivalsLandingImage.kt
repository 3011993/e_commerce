package com.example.e_commerce.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce.R
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun NewArrivalsLandingImage(modifier: Modifier = Modifier) {
    Image(painter = painterResource(R.drawable.new_arrivals), contentDescription = null,
        modifier = modifier.fillMaxWidth().heightIn(min =160.dp, max= 210.dp).padding(top = 8.dp), contentScale = ContentScale.FillWidth)
}
@Preview(showBackground = true)
@Composable
private fun NewArrivalsLandingImagePreview() {
    E_commerceTheme {
        NewArrivalsLandingImage()
    }
}