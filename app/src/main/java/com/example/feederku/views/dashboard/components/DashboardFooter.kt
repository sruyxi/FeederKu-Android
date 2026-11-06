package com.example.feederku.views.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.feederku.ui.theme.buttonGreen
import com.example.feederku.ui.theme.headerFooter

@Composable
fun DashboardFooter(
    onHomeClick: () -> Unit,
    onMapClick: () -> Unit,
    onChatClick: () -> Unit,
    onDonateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(headerFooter)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onHomeClick) {
            Icon(Icons.Default.Home, contentDescription = "Home", tint = buttonGreen)
        }
        IconButton(onClick = onMapClick) {
            Icon(Icons.Default.LocationOn, contentDescription = "Map", tint = Color.White)
        }
        IconButton(onClick = onChatClick) {
            Icon(Icons.Default.Mail, contentDescription = "Community", tint = Color.White)
        }
        IconButton(onClick = onDonateClick) {
            Icon(Icons.Default.Favorite, contentDescription = "Donate", tint = Color.White)
        }
    }
}
