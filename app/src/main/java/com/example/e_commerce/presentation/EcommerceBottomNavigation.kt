package com.example.e_commerce.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import java.util.Locale
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
                        imageVector = screen.icon,
                        contentDescription = screen.route,
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

@Composable
fun EcommerceTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(56.dp)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(
            modifier = Modifier
                .height(3.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(text = text.uppercase(Locale.getDefault()))
    }
}

@Preview(showBackground = true)
@Composable
private fun EcommerceTabPreview() {
    E_commerceTheme {
        EcommerceBottomNavigation(
            ecommerceScreens = ecommerceTabsRowScreen,
            onTabSelected = {},
            currentScreen = Store
        )
    }
}