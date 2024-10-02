package com.example.e_commerce.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommerceToolBar(
    onNavigationClickedBack: () -> Unit,
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    @DrawableRes navigationIcon: Int? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(title),
                modifier = modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = onNavigationClickedBack) {
                    Icon(
                        painter = painterResource(navigationIcon),
                        contentDescription = "Navigate back"
                    )
                }
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicToolbar(@StringRes title: Int) {
    TopAppBar(title = { Text(stringResource(title)) }, colors = TopAppBarDefaults.topAppBarColors())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionToolbar(
    modifier: Modifier,
    @StringRes title: Int,
    @DrawableRes primaryActionIcon: Int,
    primaryAction: () -> Unit,
    @DrawableRes secondaryActionIcon: Int? = null,
    secondaryAction: (() -> Unit)? = null,
) {
    TopAppBar(
        title = { Text(stringResource(title)) },
        colors = TopAppBarDefaults.topAppBarColors(),
        actions = {
            Box(modifier) {
                Row(
                    modifier = Modifier.wrapContentSize(),
                ) {
                    IconButton(onClick = primaryAction) {
                        Icon(
                            painter = painterResource(primaryActionIcon),
                            contentDescription = "Primary Action"
                        )
                    }
                    if (secondaryAction != null && secondaryActionIcon != null) {
                        IconButton(onClick = secondaryAction) {
                            Icon(
                                painter = painterResource(secondaryActionIcon),
                                contentDescription = "Secondary Action"
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun toolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): Color {
    return if (darkTheme) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
}
