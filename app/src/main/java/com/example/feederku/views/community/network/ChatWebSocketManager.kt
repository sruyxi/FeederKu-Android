package com.example.feederku.views.community.network

import com.example.feederku.views.community.model.ChatMessage
import okhttp3.*
import org.json.JSONObject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

enum class ConnectionStatus {
    Connected,
    Connecting,
    Disconnected,
    Error
}

class ChatWebSocketManager {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    private val _connectionState = MutableStateFlow<ConnectionStatus>(ConnectionStatus.Disconnected)
    val connectionState: StateFlow<ConnectionStatus> = _connectionState.asStateFlow()

    private val _incomingMessages = MutableSharedFlow<ChatMessage>(extraBufferCapacity = 64)
    val incomingMessages: SharedFlow<ChatMessage> = _incomingMessages.asSharedFlow()

    fun connect(url: String) {
        if (_connectionState.value == ConnectionStatus.Connected || _connectionState.value == ConnectionStatus.Connecting) return
        _connectionState.value = ConnectionStatus.Connecting

        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                _connectionState.value = ConnectionStatus.Connected
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val json = JSONObject(text)
                    val msg = ChatMessage(
                        id = json.optString("id", java.util.UUID.randomUUID().toString()),
                        senderId = json.optString("senderId", "unknown"),
                        senderName = json.optString("senderName", "Anonymous"),
                        text = json.optString("text", text),
                        timestamp = json.optLong("timestamp", System.currentTimeMillis()),
                        isFromMe = false
                    )
                    _incomingMessages.tryEmit(msg)
                } catch (_: Exception) {
                    val msg = ChatMessage(
                        senderId = "server",
                        senderName = "Server",
                        text = text,
                        isFromMe = false
                    )
                    _incomingMessages.tryEmit(msg)
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                _connectionState.value = ConnectionStatus.Error
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                _connectionState.value = ConnectionStatus.Disconnected
            }
        })
    }

    fun sendMessage(message: ChatMessage): Boolean {
        val json = JSONObject().apply {
            put("id", message.id)
            put("senderId", message.senderId)
            put("senderName", message.senderName)
            put("text", message.text)
            put("timestamp", message.timestamp)
        }
        return webSocket?.send(json.toString()) ?: false
    }

    fun disconnect() {
        webSocket?.close(1000, "Client disconnected")
        webSocket = null
        _connectionState.value = ConnectionStatus.Disconnected
    }
}
