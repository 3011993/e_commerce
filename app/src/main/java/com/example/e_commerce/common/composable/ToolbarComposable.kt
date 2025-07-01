package com.example.e_commerce.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommerceToolBar(
    onNavigationBack: () -> Unit,
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    @DrawableRes navigationIcon: Int? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth().height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(title),
                    modifier = modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary, fontSize = 20.sp),
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            navigationIcon = {
                if (navigationIcon != null) {
                    IconButton(
                        onClick = onNavigationBack,
                        modifier = modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface),
                    ) {
                        Icon(
                            painter = painterResource(navigationIcon),
                            contentDescription = "Navigate back",
                            tint = Color.Unspecified
                        )
                    }
                }
            },
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            windowInsets = WindowInsets(top = 0.dp)
        )
    }
}

@Composable
private fun toolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): Color {
    return if (darkTheme) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
}
