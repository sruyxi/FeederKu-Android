package com.example.feederku.views.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.dashboard.model.LiveStream

@Composable
fun LiveStreamCard(
    stream: LiveStream,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .width(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = stream.title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = textBrown,
                fontFamily = DMSans
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${stream.watching} Viewers",
                fontSize = 11.sp,
                color = textBrown.copy(alpha = 0.7f),
                fontFamily = DMSans
            )
        }
    }
}