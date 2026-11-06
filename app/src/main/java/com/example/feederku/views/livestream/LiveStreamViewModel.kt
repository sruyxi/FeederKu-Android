package com.example.feederku.views.livestream

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.feederku.views.livestream.model.ChatMessage
import com.example.feederku.views.livestream.model.LiveStreamUiState
import com.example.feederku.views.livestream.webrtc.WebRTCManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LiveStreamViewModel(application: Application) : AndroidViewModel(application) {

    private val webRTCManager = WebRTCManager(application)
    private val _uiState = MutableStateFlow<LiveStreamUiState>(LiveStreamUiState.Setup())
    val uiState: kotlinx.coroutines.flow.StateFlow<LiveStreamUiState> = _uiState.asStateFlow()


    fun updateSetup(title: String, description: String, category: String) {
            val currentState = _uiState.value
            if (currentState is LiveStreamUiState.Setup) {
                _uiState.value = currentState.copy(
                    title = title,
                    description = description,
                    category = category
            )
        }
    }

    fun startStreaming() {
        val currentState = _uiState.value
        val title = if (currentState is LiveStreamUiState.Setup && currentState.title.isNotBlank()) {
            currentState.title
        } else {
            "Feeding stray cats behind the local market"
        }

        webRTCManager.startLocalCamera()

        connectWebSocket()

        _uiState.value = LiveStreamUiState.Active(
            title = title,
            viewerCount = 8142,
            comments = listOf(
                ChatMessage("1", "Indah", "So cute! Thank you for feeding them.", "Just now"),
                ChatMessage("2", "Rendi", "Where is this block? I want to visit.", "Just now")
            )
        )
    }

    private fun connectWebSocket() {

    }

    fun addComment(senderName: String, message: String) {
        val currentState = _uiState.value
        if (currentState is LiveStreamUiState.Active) {
            val newComment = ChatMessage(
                id = System.currentTimeMillis().toString(),
                senderName = senderName,
                message = message,
                timestamp = "Just now"
            )
            _uiState.value = currentState.copy(
                comments = currentState.comments + newComment
            )
        }
    }

    fun endStreaming() {
        webRTCManager.stopCamera()
        _uiState.value = LiveStreamUiState.Ended()
    }

    fun resetToSetup() {
        _uiState.value = LiveStreamUiState.Setup()
    }
}
