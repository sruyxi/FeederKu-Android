package com.example.feederku.views.community.model

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val input: String = "",
    val currentUserId: String = "",
    val isLoading: Boolean = true,
    val isLoadingOlder: Boolean = false,
    val canLoadMore: Boolean = false,
    val loadFailed: Boolean = false,
    val errorMessage: String? = null
)