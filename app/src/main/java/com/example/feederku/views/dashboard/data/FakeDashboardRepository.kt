package com.example.feederku.views.dashboard.data

import com.example.feederku.model.FeedPost
import com.example.feederku.model.ImpactStat
import com.example.feederku.model.LiveStream
import com.example.feederku.views.dashboard.model.DashboardUiState
import kotlinx.coroutines.delay

interface DashboardRepository {
    suspend fun getDashboard(): Result<DashboardUiState>
}

object FakeDashboardRepository : DashboardRepository {
    val sampleData = DashboardUiState(
        userName = "Dhea",
        userTitle = "Certified Stray Protector",
        recentStreams = listOf(
            LiveStream(
                id = "1",
                title = "Feeding Block A Cats",
                streamerName = "Bryan Addams",
                viewerCount = 150
            ),
            LiveStream(
                id = "2",
                title = "Karawaci Feeding",
                streamerName = "NIKI",
                viewerCount = 50
            ),
            LiveStream(
                id = "3",
                title = "Karawaci Feeding",
                streamerName = "Rick Price",
                viewerCount = 50
            )
        ),
        impactStats = ImpactStat(catsFed = 60, locations = 32, activeVolunteers = 20),
        feedPosts = listOf(
            FeedPost(
                id = "1",
                authorName = "Gatton",
                timeAgo = "7 mins ago",
                content = "Found a small ginger kitten at Benton Junction. Just fed her some wet food."
            )
        )
    )

    override suspend fun getDashboard(): Result<DashboardUiState> = runCatching {
        delay(800)
        sampleData
    }
}
