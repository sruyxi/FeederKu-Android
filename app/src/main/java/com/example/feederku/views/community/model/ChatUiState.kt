package com.example.feederku.views.community.model

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val input: String = "",
    val currentUserId: String = ""
)