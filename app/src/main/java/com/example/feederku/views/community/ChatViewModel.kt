package com.example.feederku.views.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.feederku.views.community.data.ChatRepository
import com.example.feederku.views.community.data.FakeChatRepository
import com.example.feederku.views.community.model.ChatUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ChatUiState(currentUserId = repository.currentUserId)
    )
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeMessages().collect { list ->
                _uiState.update { it.copy(messages = list) }
            }
        }
    }

    fun onInputChange(text: String) {
        _uiState.update { it.copy(input = text) }
    }

    fun onSendClick() {
        val text = _uiState.value.input.trim()
        if (text.isEmpty()) return
        _uiState.update { it.copy(input = "") }
        viewModelScope.launch { repository.sendMessage(text) }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer { ChatViewModel(FakeChatRepository()) }
        }
    }
}