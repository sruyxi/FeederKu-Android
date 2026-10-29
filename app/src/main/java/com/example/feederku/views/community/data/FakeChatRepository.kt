package com.example.feederku.views.community.data

import com.example.feederku.views.community.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class FakeChatRepository : ChatRepository {

    override val currentUserId = "me"

    private val now = System.currentTimeMillis()

    private val messages = MutableStateFlow(
        listOf(
            Message("1", "u1", "Sari", "Ada yang bisa feeding di Taman Menteng sore ini?", now - 600_000),
            Message("2", "u2", "Budi", "Aku bisa, jam 5 ya. Bawa makanan basah juga.", now - 480_000),
            Message("3", "u1", "Sari", "Siap! Ada 3 kucing baru di sana kemarin.", now - 300_000)
        )
    )

    override fun observeMessages(): Flow<List<Message>> = messages

    override suspend fun sendMessage(text: String) {
        messages.update { old ->
            old + Message(
                id = UUID.randomUUID().toString(),
                senderId = currentUserId,
                senderName = "Kamu",
                text = text,
                timestamp = System.currentTimeMillis()
            )
        }
    }
}