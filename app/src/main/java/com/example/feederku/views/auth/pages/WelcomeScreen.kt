package com.example.feederku.views.auth.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.FeederkuTheme
import com.example.feederku.ui.theme.buttonGreen
import com.example.feederku.ui.theme.creamBackground
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.components.MainButton

@Composable
fun WelcomeScreen(
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(creamBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(Modifier.weight(0.9f))
        Image(
            painter = painterResource(id = com.example.feederku.R.drawable.logo_feederku),
            contentDescription = "Feederku logo",
            modifier = Modifier.size(280.dp)
        )
        Text(
            text = "Feederku",
            fontSize = 40.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.ExtraBold,
            color = textBrown
        )
        Text(
            text = "Help Stray Cats Around You!",
            fontSize = 12.sp,
            fontFamily = FontFamily.Default,
            color = textBrown
        )
        Spacer(Modifier.weight(1f))

        MainButton(
            text = "Sign Up",
            onClick = onSignUpClick
        )
        Text(
            text = "Or",
            fontSize = 11.sp,
            color = textBrown,
            modifier = Modifier.padding(vertical = 6.dp)
        )
        OutlinedButton(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(26.dp),
            border = BorderStroke(1.dp, buttonGreen),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White
            )
        ){
            Text(
                text = "Login",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = buttonGreen
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WelcomePreview() = FeederkuTheme { WelcomeScreen(onSignUpClick = {}, onLoginClick = {}) }