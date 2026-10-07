package com.example.feederku.views.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.headerFooter
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.profile.model.ProfileUser

@Composable
fun ProfileInfo(
    user: ProfileUser,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(user.imageRes),
            contentDescription = "Foto Profil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .border(
                    BorderStroke(3.dp, headerFooter), CircleShape)
                .padding(4.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.height(4.dp))

        Text(
            text = user.name,
            fontSize = 14.sp,
            fontFamily = DMSans,
            color = headerFooter,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = user.username,
            fontSize = 14.sp,
            fontFamily = DMSans,
            color = headerFooter,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(2.dp))

        Text(
            text = user.bio,
            fontSize = 12.sp,
            fontFamily = DMSans,
            color = headerFooter,
            textAlign = TextAlign.Center
        )
    }
}