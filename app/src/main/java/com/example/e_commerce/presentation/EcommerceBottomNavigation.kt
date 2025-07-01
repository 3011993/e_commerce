package com.example.e_commerce.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemGestures
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
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
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        windowInsets = WindowInsets(bottom = WindowInsets.systemGestures.asPaddingValues().calculateBottomPadding())

    ) {
        ecommerceScreens.forEach { screen ->
            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = { onTabSelected(screen) },
                icon = {
                    Icon(
                        painter = painterResource(screen.icon),
                        contentDescription = screen.route,
                        modifier = modifier.size(20.dp),
                        tint = if (currentScreen == screen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                },
                label = {
                    Text(
                        text = (screen.route.uppercase()), fontSize = 10.sp
                    )
                },
                modifier = modifier,
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        NavigationBarDefaults.Elevation
                    ),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                ),
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