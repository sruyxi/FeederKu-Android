package com.example.feederku.views.livestream.model

// Model Data Chat
data class ChatMessage(
    val id: String,
    val senderName: String,
    val message: String,
    val timestamp: String
)

data class BroadcastStats(
    val duration: String = "42 mins 15 secs",
    val totalViewers: String = "1,240",
    val peakViewers: String = "185",
    val reactions: String = "3,420"
)

sealed interface LiveStreamUiState {
    data class Setup(
        val title: String = "",
        val description: String = "",
        val category: String = "Feeding"
    ) : LiveStreamUiState

    data class Active(
        val title: String,
        val viewerCount: Int = 8142,
        val comments: List<ChatMessage> = emptyList()
    ) : LiveStreamUiState

    data class Ended(
        val stats: BroadcastStats = BroadcastStats()
    ) : LiveStreamUiState
}