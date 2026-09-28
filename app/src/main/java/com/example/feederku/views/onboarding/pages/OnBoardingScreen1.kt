package com.example.feederku.views.onboarding.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.R
import com.example.feederku.ui.theme.FeederkuTheme
import com.example.feederku.ui.theme.boxFitur
import com.example.feederku.ui.theme.boxIcon
import com.example.feederku.ui.theme.creamBackground
import com.example.feederku.ui.theme.skipOnclick
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.onboarding.model.fiturFeeder

@Composable
fun OnBoardingScreen1 (
    onSkip: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Header Foto Kucing
            Image(
                painter = painterResource(id = R.drawable.foto_kucing),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                contentScale = ContentScale.Crop
            )
        //Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .background(creamBackground)
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Feederku",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Default,
                    color = textBrown,
                    modifier = Modifier.align(Alignment.Center)
                )
                Text(
                    text = "Skip",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = skipOnclick,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 30.dp)
                        .clickable (onClick = onSkip)
                )
            }

            Text(
                text = "Help Stray Cats Around You!",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                color = textBrown,
                modifier = Modifier.padding(top = 15.dp)
            )

            Spacer(Modifier.height(40.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                fiturFeeder.forEach { fitur ->
                    fiturCard(
                        iconRes = fitur.resId,
                        title = fitur.nama,
                        description = fitur.desc
                    )
                }
            }
        }
    }
}

@Composable
private fun fiturCard(
    iconRes: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
){
    Card(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = boxFitur
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = boxIcon,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                )
            }
            Column(
                modifier = Modifier.padding(start = 20.dp)
            ) {
                Text(
                    text = title,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = textBrown
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = description,
                    fontFamily = FontFamily.Default,
                    color = textBrown,
                    style = TextStyle(fontSize = 10.sp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoarding1Preview() {
    FeederkuTheme {
        OnBoardingScreen1(onSkip = {})
    }
}