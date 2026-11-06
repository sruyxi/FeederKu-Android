package com.example.feederku.views.livestream.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.DMSans

@Composable
fun LiveChatBubble(sender: String, message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.4f), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = sender,
            color = Color.Green,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            fontFamily = DMSans
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = message,
            color = Color.White,
            fontSize = 13.sp,
            fontFamily = DMSans
        )
    }
}