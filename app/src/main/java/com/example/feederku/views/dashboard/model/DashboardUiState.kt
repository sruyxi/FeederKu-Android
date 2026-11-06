package com.example.feederku.views.dashboard.model

import com.example.feederku.model.FeedPost
import com.example.feederku.model.ImpactStat
import com.example.feederku.model.LiveStream

enum class DashboardTab {
    Home,
    Map,
    Chat,
    Donate
}

data class DashboardUiState(
    val userName: String = "Sarah",
    val userTitle: String = "Certified Stray Protector",
    val searchQuery: String = "",
    val selectedTab: DashboardTab = DashboardTab.Home,
    val recentStreams: List<LiveStream> = emptyList(),
    val impactStats: ImpactStat = ImpactStat(catsFed = 89, locations = 12, activeVolunteers = 10),
    val feedPosts: List<FeedPost> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
