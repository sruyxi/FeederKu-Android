package com.example.feederku.views.dashboard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.DMSans
import com.example.feederku.ui.theme.buttonGreen
import com.example.feederku.ui.theme.headerFooter
import com.example.feederku.ui.theme.searchBar
import com.example.feederku.ui.theme.textBrown

@Composable
fun SearchBarSection(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                4.dp,
                RoundedCornerShape(24.dp)
            ),
        placeholder = {
            Text(
                "Search stray locations...",
                fontSize = 14.sp,
                fontFamily = DMSans
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors( //menentukan warna search bar saat aktif, tidak aktif, teks, cursor, icon, dll
            focusedContainerColor = searchBar,
            unfocusedContainerColor = searchBar,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = headerFooter,
            unfocusedTextColor = headerFooter,
            cursorColor = buttonGreen,
            focusedPlaceholderColor = headerFooter.copy(alpha = 0.5f),
            unfocusedPlaceholderColor = headerFooter.copy(alpha = 0.5f),
            focusedLeadingIconColor = headerFooter.copy(alpha = 0.7f),
            unfocusedLeadingIconColor = headerFooter.copy(alpha = 0.7f)
        )
    )
}