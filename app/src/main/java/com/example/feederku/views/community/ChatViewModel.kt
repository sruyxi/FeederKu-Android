package com.example.feederku.views.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.feederku.views.community.data.ChatRepository
import com.example.feederku.views.community.data.FirebaseChatRepository
import com.example.feederku.views.community.model.ChatUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val limit = MutableStateFlow(PAGE_SIZE)
    private var observeJob: Job? = null

    private val _uiState = MutableStateFlow(
        ChatUiState(currentUserId = repository.currentUserId)
    )
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        startObserving()
    }

    // Whenever the limit changes, the listener restarts with the new limit
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun startObserving() {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            limit
                .flatMapLatest { n ->
                    repository.observeMessages(n).map { list -> n to list }
                }
                .catch {
                    _uiState.update { s ->
                        s.copy(
                            isLoading = false,
                            isLoadingOlder = false,
                            loadFailed = true,
                            errorMessage = "Couldn't load messages. Check your connection."
                        )
                    }
                }
                .collect { (n, list) ->
                    _uiState.update { s ->
                        s.copy(
                            messages = list,
                            isLoading = false,
                            isLoadingOlder = false,
                            loadFailed = false,
                            // If we got a full page, there may be older messages
                            canLoadMore = list.size >= n
                        )
                    }
                }
        }
    }

    fun retry() {
        _uiState.update { it.copy(isLoading = true, loadFailed = false) }
        startObserving()
    }

    fun onLoadOlderClick() {
        if (_uiState.value.isLoadingOlder) return
        _uiState.update { it.copy(isLoadingOlder = true) }
        limit.update { it + PAGE_SIZE }
    }

    fun onInputChange(text: String) {
        if (text.length > MAX_MESSAGE_LENGTH) return
        _uiState.update { it.copy(input = text) }
    }

    fun onSendClick() {
        val text = _uiState.value.input.trim()
        if (text.isEmpty() || text.length > MAX_MESSAGE_LENGTH) return

        _uiState.update { it.copy(input = "") }
        viewModelScope.launch {
            try {
                repository.sendMessage(text)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Give the text back so the user doesn't have to retype it
                _uiState.update { s ->
                    s.copy(
                        input = if (s.input.isBlank()) text else s.input,
                        errorMessage = "Message failed to send. Please try again."
                    )
                }
            }
        }
    }

    fun onErrorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    companion object {
        const val PAGE_SIZE = 50
        const val MAX_MESSAGE_LENGTH = 500

        val Factory = viewModelFactory {
            initializer { ChatViewModel(FirebaseChatRepository()) }
        }
    }
}