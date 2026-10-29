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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.auth.components.AuthLayout
import com.example.feederku.views.auth.components.MainButton
import com.example.feederku.views.auth.model.AuthUiState

@Composable
fun VerifyEmailScreen(
    uiState: AuthUiState,
    onBackToLoginClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val scrollState = rememberScrollState()

    AuthLayout(
        onBackClick = onBackClick,
        modifier = modifier,
        bottomContent = {
            MainButton(
                text = "Back to Login",
                onClick = onBackToLoginClick,
                enabled = true
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ){
            Text(
                text = "Check Your Email",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = textBrown,
                fontFamily = DMSans
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "We have sent password recovery instructions to ${uiState.email.ifBlank { "your registered email"}}.",
                fontSize = 12.sp,
                color = textBrown,
                fontFamily = DMSans
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Did not receive the email? Check your spam folder or make sure the email address was correct.",
                fontSize = 12.sp,
                color = textBrown.copy(alpha = 0.7f),
                fontFamily = DMSans
            )
        }
    }
}