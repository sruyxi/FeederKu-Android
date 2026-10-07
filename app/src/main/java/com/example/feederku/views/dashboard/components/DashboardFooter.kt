package com.example.feederku.views.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.feederku.ui.theme.creamBackground
import com.example.feederku.ui.theme.headerFooter
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.dashboard.model.DashboardTab

private fun DashboardTab.icon(): ImageVector = when (this) {
    DashboardTab.Home -> Icons.Default.Home
    DashboardTab.Locations -> Icons.Default.LocationOn
    DashboardTab.Messages -> Icons.Default.ChatBubbleOutline
    DashboardTab.Favorites -> Icons.Default.FavoriteBorder
}

@Composable
fun DashboardFooter(
    selectedTab: DashboardTab,
    onTabClick: (DashboardTab) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(headerFooter)
            .navigationBarsPadding()
            .padding(
                vertical = 12.dp,
                horizontal = 24.dp
            ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ){
        DashboardTab.entries.forEach { tab -> // ambil semua pilihan yang ada di dashboard Tab (Home, Locations, Messages, Favorites)
            val selected = tab == selectedTab // cek apakah tab yg sedang diproses adalah tab yg sedang di pilih?
            IconButton(
                onClick = { onTabClick(tab) },
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        if (selected) creamBackground.copy(alpha = 0.15f) else Color.Transparent,
                        RoundedCornerShape(14.dp)
                        )
            ){
                Icon(
                    imageVector = tab.icon(),
                    contentDescription = tab.label,
                    tint = if (selected) Color.White else Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}