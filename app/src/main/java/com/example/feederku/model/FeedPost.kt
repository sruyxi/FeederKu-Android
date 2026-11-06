package com.example.feederku.model

data class FeedPost(
    val id: String,
    val authorName: String,
    val timeAgo: String,
    val content: String,
    val imageUrl: String? = null
)