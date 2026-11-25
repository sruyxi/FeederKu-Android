package com.example.feederku.views.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.feederku.model.LiveStream
import com.example.feederku.ui.theme.*
import com.example.feederku.views.dashboard.components.LiveStreamCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeAllStreamsScreen(
    onBackClick: () -> Unit,
    onStreamClick: (String) -> Unit,
    viewModel: DashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val allStreams = uiState.recentStreams + listOf(
        LiveStream("4", "Feeding Stray Cats at Park", "Sarah C.", 85),
        LiveStream("5", "Rescue Mission Block C", "Budi U.", 120),
        LiveStream("6", "Evening Feeding Routine", "Dewi L.", 64),
        LiveStream("7", "Karawaci Cat Colony", "Rendi K.", 92)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recent Live Streams", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = headerFooter,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = ColorFogCream
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(allStreams) { stream ->
                LiveStreamCard(
                    stream = stream,
                    onClick = { onStreamClick(stream.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
