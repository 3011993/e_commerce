package com.example.e_commerce.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun EcommerceBottomNavigation(
    ecommerceScreens: List<EcommerceDestination>,
    onTabSelected: (EcommerceDestination) -> Unit,
    currentScreen: EcommerceDestination,
    modifier: Modifier = Modifier
) {
    BottomNavigation (
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
        ecommerceScreens.forEach { screen ->
            BottomNavigationItem(
                selected = currentScreen == screen,
                onClick = { onTabSelected(screen) },
                icon = {
                    Icon(
                        painter = painterResource(screen.icon),
                        contentDescription = screen.route,
                        modifier = modifier.size(20.dp)
                    )
                },
                label = { Text(text = (screen.route.uppercase())
                    , fontSize = 10.sp) }
                ,
                modifier = modifier,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EcommerceTabPreview() {
    E_commerceTheme {
        EcommerceBottomNavigation(
            ecommerceScreens = ecommerceTabsRowScreen,
            onTabSelected = {},
            currentScreen = Home
        )
    }
}