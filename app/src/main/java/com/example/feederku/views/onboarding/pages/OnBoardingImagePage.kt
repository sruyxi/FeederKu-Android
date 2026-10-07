package com.example.feederku.views.onboarding.pages

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.creamBackground
import com.example.feederku.ui.theme.textBrown

@Composable
fun OnBoardingImagePage(
    @DrawableRes imageRes: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .background(creamBackground)
                .padding(vertical = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(75.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(240.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Feed Strays Near You",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = DMSans,
                color = textBrown,
                modifier = Modifier.padding(top = 10.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Locate feeding points and coordinates with " +
                        "fellow cat lovers to make sure no stary cats " +
                        "goes hungry tonight",
                fontSize = 12.sp,
                fontFamily = DMSans,
                color = textBrown,
                modifier = Modifier
                    .padding(horizontal = 70.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}