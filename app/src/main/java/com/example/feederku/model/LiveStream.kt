package com.example.feederku.model

data class LiveStream(
    val id: String,
    val title: String,
    val streamerName: String,
    val viewerCount: Int,
    val isLive: Boolean = true,
    val category: String = "Feeding"
)