package com.example.feederku.views.livestream.components

import androidx.compose.foundation.layout.*
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
import com.example.feederku.views.livestream.model.BroadcastStats
import com.example.feederku.ui.theme.textBrown

@Composable
fun StreamSummaryCard(
    stats: BroadcastStats,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Broadcast Statistics", fontWeight = FontWeight.Bold, color = textBrown)
            StatRow("Duration", stats.duration)
            StatRow("Total Viewers", stats.totalViewers)
            StatRow("Peak Viewers", stats.peakViewers)
            StatRow("Reactions", stats.reactions)
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = textBrown.copy(alpha = 0.7f), fontSize = 13.sp)
        Text(text = value, fontWeight = FontWeight.Bold, color = textBrown, fontSize = 13.sp)
    }
}