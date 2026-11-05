package com.example.feederku.views.community.data

import com.example.feederku.views.community.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import kotlinx.coroutines.flow.map

class FakeChatRepository : ChatRepository {

    override val currentUserId = "me"

    private val now = System.currentTimeMillis()

    private val messages = MutableStateFlow(
        listOf(
            Message("1", "u1", "Sari", "Anyone available for feeding at Menteng Park this afternoon?", now - 600_000),
            Message("2", "u2", "Budi", "I can make it at 5 PM! Bring wet food as well.", now - 480_000),
            Message("3", "u1", "Sari", "Got it! Spotted 3 new cats there yesterday.", now - 300_000)
        )
    )

    override fun observeMessages(limit: Int): Flow<List<Message>> =
        messages.map { it.takeLast(limit) }

    override suspend fun sendMessage(text: String) {
        messages.update { old ->
            old + Message(
                id = UUID.randomUUID().toString(),
                senderId = currentUserId,
                senderName = "You",
                text = text,
                timestamp = System.currentTimeMillis()
            )
        }
    }
}