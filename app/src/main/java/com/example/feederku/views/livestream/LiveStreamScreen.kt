package com.example.feederku.views.livestream

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feederku.views.livestream.model.LiveStreamUiState
import com.example.feederku.views.livestream.screens.ActiveLiveScreen
import com.example.feederku.views.livestream.screens.StartLiveScreen
import com.example.feederku.views.livestream.screens.StreamEndedScreen

@Composable
fun LiveStreamRoute(
    modifier: Modifier = Modifier,
    onBackToHome: () -> Unit = {},
    viewModel: LiveStreamViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        when (val state = uiState) {
            is LiveStreamUiState.Setup -> {
                StartLiveScreen(
                    onStartClick = { title, desc, cat ->
                        viewModel.updateSetup(title, desc, cat)
                        viewModel.startStreaming()
                    },
                    onCancel = onBackToHome
                )
            }
            is LiveStreamUiState.Active -> {
                ActiveLiveScreen(
                    uiState = state,
                    onEndClick = { viewModel.endStreaming() },
                    onSendComment = { msg -> viewModel.addComment("Sarah", msg) }
                )
            }
            is LiveStreamUiState.Ended -> {
                StreamEndedScreen(
                    stats = state.stats,
                    onBackToHome = onBackToHome
                )
            }
        }
    }
}