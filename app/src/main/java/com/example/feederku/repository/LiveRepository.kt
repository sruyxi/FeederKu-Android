package com.example.feederku.repository

import com.example.feederku.views.livestream.model.ChatMessage

class LiveRepository {
    fun getInitialComments(): List<ChatMessage> {
        return listOf(
            ChatMessage("1", "Indah", "So cute! Thank you for feeding them.", "Just now"),
            ChatMessage("2", "Rendi", "Where is the block? I want to visit.", "Just now")
        )
    }
}