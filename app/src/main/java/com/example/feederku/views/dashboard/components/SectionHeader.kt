package com.example.feederku.views.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.buttonGreen
import com.example.feederku.ui.theme.textBrown

@Composable
fun SectionHeader(
    title: String,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textBrown,
            fontFamily = DMSans
        )
        Text(
            text = "See All",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = buttonGreen,
            fontFamily = DMSans,
            modifier = Modifier.clickable { onSeeAllClick() }
        )
    }
}
