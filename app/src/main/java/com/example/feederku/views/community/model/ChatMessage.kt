package com.example.feederku.views.community.model

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val senderId: String,
    val senderName: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isFromMe: Boolean = false
)
