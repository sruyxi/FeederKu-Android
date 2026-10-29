package com.example.feederku.views.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.components.InputField
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.auth.components.AuthLayout
import com.example.feederku.views.auth.components.MainButton
import com.example.feederku.views.auth.model.AuthUiState

@Composable
fun ForgotPasswordScreen(
    uiState: AuthUiState,
    onEmailChange: (String) -> Unit,
    onSendResetLinkClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    AuthLayout(
        onBackClick = onBackClick,
        modifier = modifier,
        bottomContent = {
            MainButton(
                text = "Send Reset Link",
                onClick = onSendResetLinkClick,
                enabled = uiState.isEmailValid,
                isLoading = uiState.isLoading
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            Text(
                text = "Forgot Password",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = textBrown,
                fontFamily = DMSans,
            )
            Text (
                text = "Don't worry! Enter your email below to receive a secure recovery link.",
                fontSize = 12.sp,
                color = textBrown,
                fontFamily = DMSans
            )
            Spacer(Modifier.height(20.dp))

            InputField(
                label = "Email Address",
                value = uiState.email,
                onValueChange = onEmailChange,
                placeholder = "sarah@example.com",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                errorMessage = if (uiState.email.isNotEmpty() && !uiState.isEmailValid){
                    "Format email tidak valid"
                } else null
            )
        }
    }
}