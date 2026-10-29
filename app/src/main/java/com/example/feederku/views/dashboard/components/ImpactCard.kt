package com.example.feederku.views.dashboard.components

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
import com.example.feederku.ui.theme.buttonGreen
import com.example.feederku.ui.theme.greenDark
import com.example.feederku.ui.theme.textBrown
import com.example.feederku.views.dashboard.model.ImpactStat

@Composable
fun ImpactCard(
    stat: ImpactStat,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.width(130.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ){
        Column(modifier = Modifier.padding(16.dp)){
            Text(
                text = stat.value,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = if (stat.highlight) buttonGreen else textBrown,
                fontFamily = DMSans
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stat.label,
                fontSize = 12.sp,
                color = textBrown,
                fontFamily = DMSans
            )
        }
    }
}