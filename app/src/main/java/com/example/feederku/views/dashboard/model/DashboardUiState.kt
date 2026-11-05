package com.example.feederku.views.dashboard.model

import androidx.annotation.DrawableRes

data class LiveStream(
    val id: Int,
    val title: String,
    val host: String,
    val watching: Int,
    @DrawableRes val thumbnailRes: Int? = null // ubah thumbnailUrl: String? dari backend
)

data class ImpactStat(
    val value: String,
    val label: String,
    val highlight: Boolean = false
)

data class FeedPost( // data class -> for storing feed post data
    val id: Int,
    val author: String,
    val timeAgo: String,
    val content: String,
    @DrawableRes val authorAvatarRes: Int? = null,
    @DrawableRes val imageRes: Int? = null
)

enum class DashboardTab(val label: String){ // enum class -> predefined tab selections
    Home("Home"),
    Locations("Locations"),
    Messages("Messages"),
    Favorites("Favorites")
}

data class DashboardUiState(
    val userName: String = "",
    val userTitle: String = "",
    val liveStream: List<LiveStream> = emptyList(),
    val impactStats: List<ImpactStat> = emptyList(),
    val feedPosts: List<FeedPost> = emptyList(),
    val searchQuery: String = "",
    val selectedTab: DashboardTab = DashboardTab.Home,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)