package com.example.feederku.views.dashboard.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.FeederkuTheme
import com.example.feederku.ui.theme.cardLinen
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.dashboard.DashboardScreen

@Composable
fun ProfileCircle(
    name: String,
    size: Dp,
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int? = null
){
    if (imageRes != null){
        Image(
            painter = painterResource(imageRes),
            contentDescription = "Foto $name",
            contentScale = ContentScale.Crop,
            modifier = modifier.size(size).clip(CircleShape)
        )
    } else {
        Box(
            modifier = modifier.size(size).clip(CircleShape).background(cardLinen),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = name.take(1).uppercase(),
                color = textBrown,
                fontWeight = FontWeight.Bold,
                fontSize = (size.value * 0.4f).sp,
                fontFamily = DMSans
            )
        }
    }
}