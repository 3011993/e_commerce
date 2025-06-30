package com.example.e_commerce.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.R.string as AppText

@Composable
fun CustomizedCommerceButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    action: () -> Unit,
    enabled: Boolean = true
) {
    CustomizedCommerceButtonWithData(
        text = text,
        modifier = modifier,
        data = null,
        action = { action() },
        enabled = enabled)
}

@Composable
fun <T> CustomizedCommerceButtonWithData(
    @StringRes text: Int, modifier: Modifier = Modifier,
    data: T? = null,
    action: (T?) -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = { action(data) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp)
            .height(50.dp),
        shape = CircleShape,
        enabled = enabled
    ) {
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
        )
    }
}

@Composable
fun BasicButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
    ) {
        Text(text = stringResource(text), fontSize = 16.sp)
    }
}

@Composable
fun DialogConfirmButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
    ) {
        Text(text = stringResource(text), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun DialogCancelButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
    ) {
        Text(text = stringResource(text), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun DangerousDialogConfirmButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
    ) {
        Text(text = stringResource(text), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun DangerousDialogCancelButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
    ) {
        Text(text = stringResource(text), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun DialogConfirmLoginButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
    ) {
        Text(text = stringResource(text), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun DialogCancelLoginButton(action: () -> Unit) {
    Button(
        onClick = action,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
    ) {
        Text(text = stringResource(AppText.cancel), style = MaterialTheme.typography.bodyMedium)
    }
}