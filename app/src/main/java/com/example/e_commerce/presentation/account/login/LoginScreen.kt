package com.example.e_commerce.presentation.account.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.common.composable.EmailField
import com.example.e_commerce.common.composable.PasswordField
import com.example.e_commerce.ui.theme.E_commerceTheme
import com.example.e_commerce.ui.theme.secondaryOnBackGround
import com.example.e_commerce.ui.theme.warningColor

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { viewModel.onSignInClick(openAndPopUp) },
        onForgotPasswordClick = viewModel::onForgotPasswordClick
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            "Welcome",
            modifier
                .fillMaxWidth()
                .padding(top = 105.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            "Enter your credentials to Login",
            modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(color = secondaryOnBackGround)
        )
        Text(
            "Email Address",
            style = MaterialTheme.typography.labelSmall.copy(color = secondaryOnBackGround),
            modifier = modifier.padding(start = 16.dp, top = 100.dp)
        )
        EmailField(
            uiState.email,
            onEmailChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 16.dp, end = 16.dp)
        )
        Text(
            "Password",
            style = MaterialTheme.typography.labelSmall.copy(color = secondaryOnBackGround),
            modifier = modifier
                .padding(
                    start = 16.dp,
                    top = 8.dp
                )
        )
        PasswordField(
            uiState.password, onPasswordChange, modifier = modifier
                .fillMaxWidth()
                .padding(
                    top = 4.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        )
        TextButton(
            onClick = onForgotPasswordClick,
            modifier = modifier
                .align(Alignment.End)

        ) {
            Text(
                "Forget Password?",
                color = warningColor,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(modifier.weight(1f))
        Button(
            onClick = onSignInClick,
            modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(bottom = 0.dp),
            shape = RectangleShape
        ) {
            Text(
                "Login",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val uiState = LoginUiState(
        email = "email@test.com"
    )

    E_commerceTheme {
        LoginScreenContent(
            uiState = uiState,
            onEmailChange = { },
            onPasswordChange = { },
            onSignInClick = { },
            onForgotPasswordClick = { }
        )
    }
}
