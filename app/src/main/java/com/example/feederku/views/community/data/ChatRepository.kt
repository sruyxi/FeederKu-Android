package com.example.feederku.views.community.data

import com.example.feederku.views.community.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    val currentUserId: String
    fun observeMessages(limit: Int): Flow<List<Message>>
    suspend fun sendMessage(text: String)
}