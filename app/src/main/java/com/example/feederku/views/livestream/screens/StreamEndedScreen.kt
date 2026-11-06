package com.example.feederku.views.livestream.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.*
import com.example.feederku.views.livestream.components.StreamSummaryCard
import com.example.feederku.views.livestream.model.BroadcastStats

@Composable
fun StreamEndedScreen(
    stats: BroadcastStats,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(creamBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Stream Ended",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textBrown,
            fontFamily = DMSans
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Great job! Your live broadcast was successfully published.",
            fontSize = 14.sp,
            color = textBrown.copy(alpha = 0.7f),
            fontFamily = DMSans
        )
        Spacer(modifier = Modifier.height(24.dp))

        StreamSummaryCard(stats = stats)

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onBackToHome,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = buttonGreen),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Back to home", fontWeight = FontWeight.Bold, color = androidx.compose.ui.graphics.Color.White)
        }
    }
}