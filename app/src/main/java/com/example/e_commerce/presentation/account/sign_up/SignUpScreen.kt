package com.example.e_commerce.presentation.account.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.common.composable.EmailField
import com.example.e_commerce.common.composable.PasswordField
import com.example.e_commerce.common.composable.RepeatPasswordField
import com.example.e_commerce.ui.theme.E_commerceTheme

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) }
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 105.dp),
    ) {
        Text(
            "Sign Up",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 28.sp,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            "Email Address",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Gray,
            modifier = modifier
                .height(14.dp)
                .padding(start = 16.dp)
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
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Gray,
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

        Text(
            "Repeat Password",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Gray,
            modifier = modifier
                .padding(
                    start = 16.dp,
                    top = 8.dp
                )
        )
        RepeatPasswordField(
            uiState.repeatPassword,
            onRepeatPasswordChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp, top = 4.dp, end = 16.dp
                )
        )
        Spacer(modifier.weight(1f))
        Button(
            onClick = onSignUpClick,
            modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(bottom = 0.dp),
            shape = RectangleShape
        ) {
            Text("Sign Up",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val uiState = SignUpUiState(
        email = "email@test.com"
    )

    E_commerceTheme {
        SignUpScreenContent(
            uiState = uiState,
            onEmailChange = { },
            onPasswordChange = { },
            onRepeatPasswordChange = { },
            onSignUpClick = { }
        )
    }
}
