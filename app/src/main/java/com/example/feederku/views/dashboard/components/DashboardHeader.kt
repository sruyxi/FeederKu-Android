package com.example.feederku.views.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.R
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.buttonGreen
import com.example.feederku.ui.theme.headerFooter
import com.example.feederku.ui.theme.textBrown

@Composable
fun DashboardHeader(
    userName: String,
    userTitle: String,
    onAddClick: () -> Unit,
    onProfileClick: () -> Unit,
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
        // Bagian Icon Foto + Nama + Title
        Row(
            modifier = Modifier.clickable {
                onProfileClick()
            },
            verticalAlignment = Alignment.CenterVertically) {
            ImagePlaceholder(
               imageRes = R.drawable.profile_circle,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = if (userName.isBlank()) "Hola!" else "Hola, $userName!",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = DMSans
                )
                Text(
                    text = userTitle,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    fontFamily = DMSans
                )
            }
        }
     // Tombol +
        IconButton(
            onClick = onAddClick,
            modifier = Modifier
                .size(40.dp)
                .shadow(
                    12.dp,
                    CircleShape,
                    ambientColor = buttonGreen,
                    spotColor = buttonGreen
                )
                .background(
                    buttonGreen,
                    CircleShape
                )
        ){
            Icon(
                Icons.Default.Add,
                contentDescription = "Tambah laporan",
                tint = Color.White
            )
        }
    }
}