package com.example.feederku.views.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.model.FeedPost
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.textBrown

@Composable
fun FeedPostCard(
    post: FeedPost,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                    Column {
                        Text(
                            text = post.authorName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = textBrown,
                            fontFamily = DMSans
                        )
                        Text(
                            text = post.timeAgo,
                            fontSize = 11.sp,
                            color = textBrown.copy(alpha = 0.6f),
                            fontFamily = DMSans
                        )
                    }
                }
                Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = textBrown)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = post.content,
                fontSize = 13.sp,
                color = textBrown,
                fontFamily = DMSans
            )

            Spacer(modifier = Modifier.height(12.dp))
            // Gambar postingan
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
            )
        }
    }
}