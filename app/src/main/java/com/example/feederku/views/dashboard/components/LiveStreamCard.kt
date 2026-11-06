package com.example.feederku.views.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.model.LiveStream
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.textBrown

@Composable
fun LiveStreamCard(
    stream: LiveStream,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Thumbnail placeholder & Live Badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(Color.LightGray)
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Red)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "• LIVE",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = stream.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = textBrown,
                    maxLines = 1,
                    fontFamily = DMSans
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "By ${stream.streamerName}",
                    fontSize = 11.sp,
                    color = textBrown.copy(alpha = 0.7f),
                    fontFamily = DMSans
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${stream.viewerCount} watching",
                    fontSize = 10.sp,
                    color = textBrown.copy(alpha = 0.5f),
                    fontFamily = DMSans
                )
            }
        }
    }
}