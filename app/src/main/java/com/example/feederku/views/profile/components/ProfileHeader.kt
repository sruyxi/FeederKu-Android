package com.example.feederku.views.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.headerFooter

@Composable
fun ProfileHeader(
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(headerFooter)
            .statusBarsPadding()
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(40.dp)
                .border(
                    1.dp,
                    Color.White,
                    CircleShape
                )
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = Color.White
            )
        }
        Text(
            text = "My Profile",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = DMSans
        )

        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .size(40.dp)
                .border(
                    1.dp,
                    Color.White,
                    CircleShape
                )
        ){
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Pengaturan",
                tint = Color.White
            )
        }
    }
}