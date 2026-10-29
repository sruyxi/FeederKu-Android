package com.example.feederku.views.dashboard.data

import com.example.feederku.views.dashboard.model.DashboardUiState
import com.example.feederku.views.dashboard.model.FeedPost
import com.example.feederku.views.dashboard.model.ImpactStat
import com.example.feederku.views.dashboard.model.LiveStream
import kotlinx.coroutines.delay

interface DashboardRepository {
    suspend fun getDashboard(): Result<DashboardUiState>
}

object FakeDashboardRepository : DashboardRepository {
    val sampleData = DashboardUiState(
        userName = "Dhea",
        userTitle = "Certified Stray Protector",
        liveStream = listOf(
            LiveStream(
                id = 1,
                title = "Feeding Block A Cats",
                host = "Bryan Addams",
                watching = 150
            ),
            LiveStream(
                id = 2,
                title = "Karawaci Feeding",
                host = "NIKI",
                watching = 50
            ),
            LiveStream(
                id = 3,
                title = "Karawaci Feeding",
                host = "Rick Price",
                watching = 50
            )
        ),
        impactStats = listOf(
            ImpactStat(value = "60", label = "Cats Fed"),
            ImpactStat(value = "32", label = "Locations"),
            ImpactStat(value = "20", label = "Cats Fed", highlight = true)
        ),
        feedPosts = listOf(
            FeedPost(
                id = 1,
                author = "Gatton",
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