package com.example.feederku.views.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.FeederkuTheme
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.data.remote.getGoogleIdToken
import com.example.feederku.views.auth.model.AuthUiState
import com.example.feederku.views.auth.components.AuthLayout
import com.example.feederku.views.auth.components.AuthSwitchText
import com.example.feederku.ui.components.InputField
import com.example.feederku.views.auth.components.MainButton
import com.example.feederku.views.auth.components.SocialLoginButtons
import kotlinx.coroutines.launch


//ROUTE
@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit, //navigasi ke Home-Dashboard
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.isAuthenticated){
        if(uiState.isAuthenticated) onLoginSuccess()
    }
    LoginScreen(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::onLoginClick,
        onBackClick = onBackClick,
        onSignUpClick = onSignUpClick,
        onForgotPasswordClick = onForgotPasswordClick,
        onGoogleClick = {
            scope.launch {
                getGoogleIdToken(context,)
                    .onSuccess(viewModel::onGoogleIdToken)
                    .onFailure { viewModel.onGoogleError(it.message) }
            }
        },
        modifier = modifier
    )
}
//SCREEN
@Composable
fun LoginScreen(
    uiState: AuthUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onGoogleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AuthLayout(
        onBackClick = onBackClick,
        modifier = modifier,
        bottomContent = {
            MainButton(
                text = "Login",
                onClick = onLoginClick,
                enabled = uiState.canLogin,
                isLoading = uiState.isLoading
            )
            SocialLoginButtons(
                dividerText = "Or Login with",
                onGoogleClick = onGoogleClick
            )
            AuthSwitchText(
                question = "Don't have an account?",
                linkText = "Sign Up",
                onLinkClick = onSignUpClick
            )
        }
    ) {
        Text(
            text = "Welcome Back!",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = textBrown,
            fontFamily = DMSans
        )
        Text(
            text = "Login to check your local animal alerts",
            fontSize = 12.sp,
            color = textBrown,
            fontFamily = DMSans
        )
        Spacer(Modifier.height(20.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //Input Email
            InputField(
                label = "Email Address",
                value = uiState.email,
                onValueChange = onEmailChange,
                placeholder = "e.g. harry@example.com",
                keyboardType = KeyboardType.Email,
                errorMessage = if (uiState.email.isNotEmpty() && !uiState.isEmailValid) {
                    "Format email tidak valid"
                } else {
                    null
                }
            )

            InputField(
                label = "Password",
                value = uiState.password,
                onValueChange = onPasswordChange,
                placeholder = "Minimum 8 character",
                keyboardType = KeyboardType.Password,
                isPassword = true,
                imeAction = ImeAction.Done
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                Text(
                    text = "Forgot Password?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = textBrown,
                    fontFamily = DMSans,
                    modifier = Modifier.clickable { onForgotPasswordClick() }
                )
            }

            uiState.errorMessage?.let { errorMsg ->
                Text(
                    text = errorMsg,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun LoginPreviewContent(state: AuthUiState) {
    FeederkuTheme {
        LoginScreen(
            uiState = state,
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onBackClick = {},
            onSignUpClick = {},
            onForgotPasswordClick = {},
            onGoogleClick = {}
        )
    }
}

@Preview(name = "Kosong", showBackground = true, showSystemUi = true)
@Composable
private fun LoginEmptyPreview() = LoginPreviewContent(AuthUiState())

@Preview(name = "Terisi", showBackground = true, showSystemUi = true)
@Composable
private fun LoginFilledPreview() =
    LoginPreviewContent(AuthUiState(email = "sarah@example.com", password = "adadeh123"))
