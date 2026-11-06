package com.example.feederku.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.feederku.ui.theme.headerFooter

@Composable
fun BottomNavbar(){
    NavigationBar(
        containerColor = headerFooter,
        contentColor = Color.White
    ){
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = Color.LightGray) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Chat", tint = Color.LightGray) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite", tint = Color.LightGray) }
        )
    }
}