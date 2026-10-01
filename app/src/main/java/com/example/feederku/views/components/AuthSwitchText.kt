package com.example.feederku.views.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.skipOnclick
import com.example.feederku.ui.theme.textBrown

@Composable
fun AuthSwitchText(
    question: String,
    linkText: String,
    onLinkClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$question ",
            fontSize = 11.sp,
            color = textBrown
        )
        Text(
            text = linkText,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = skipOnclick,
            modifier = Modifier.clickable(onClick = onLinkClick)
        )
    }
}
