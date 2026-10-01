package com.example.feederku.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.creamBackground
import com.example.feederku.ui.theme.textBrown

//Kerangka layar: header coklat, area content, dan footer
@Composable
fun AuthLayout(
    onBackClick: () -> Unit,
    bottomContent: @Composable ColumnScope.() -> Unit, // tombol, "Or ... with, link
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit //judul + form
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(creamBackground)
            .imePadding() //naik ketika keyboard muncul
    ){
        AuthHeader(onBackClick = onBackClick)

        //Area form - bisa discroll kalo layar kecil atau ketika keyboard terbuka
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(state = rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            content = content
        )

        //Footer: selalu menempel di bawah layar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = bottomContent
        )
    }

}

@Composable
fun AuthHeader(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = textBrown
            )
            .statusBarsPadding() //supaya coklat tetap sampai ke belakang status bar
            .height(72.dp)
            .padding(horizontal = 8.dp)
    ){
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = creamBackground
            )
        }
        Text(
            text = "Feederku",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = creamBackground,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}