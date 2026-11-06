package com.example.feederku.views.livestream.screens

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview as CameraPreview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.feederku.views.livestream.model.LiveStreamUiState
import com.example.feederku.views.livestream.components.LiveChatBubble
import com.example.feederku.views.livestream.components.LiveChatInput
import com.example.feederku.views.livestream.components.LiveHeaderOverlay

@Composable
fun ActiveLiveScreen(
    uiState: LiveStreamUiState.Active,
    onEndClick: () -> Unit,
    onSendComment: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // 1. Kamera Preview sebagai Background Full Screen
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { previewView ->
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = CameraPreview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview
                        )
                    } catch (exc: Exception) {
                        // Handle exception
                    }
                }, ContextCompat.getMainExecutor(context))
            }
        )

        // 2. Header Overlay (Badge LIVE, Jumlah Penonton, dan Tombol End)
        LiveHeaderOverlay(
            viewerCount = uiState.viewerCount,
            onEndClick = onEndClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
        )

        // 3. Bagian Bawah: Daftar Komentar & Kolom Input
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                reverseLayout = true
            ) {
                items(uiState.comments.reversed()) { chat ->
                    LiveChatBubble(
                        sender = chat.senderName,
                        message = chat.message
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LiveChatInput(
                onSendComment = onSendComment
            )
        }
    }
}
