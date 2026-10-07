package com.example.feederku.views.dashboard.data

import com.example.feederku.views.dashboard.model.FeedPost
import com.example.feederku.views.dashboard.model.ImpactStat
import com.example.feederku.views.dashboard.model.LiveStream
import kotlinx.coroutines.delay

data class DashboardData(
    val userName: String,
    val userTitle: String,
    val liveStream: List<LiveStream>,
    val impactStats: List<ImpactStat>,
    val feedPosts: List<FeedPost>
)

interface DashboardRepository{
    suspend fun getDashboard(): Result<DashboardData>
}

object FakeDashboardRepository : DashboardRepository{
    val sampleData = DashboardData(
        userName = "Dhea",
        userTitle = "Certified Stray Protector",
        liveStream = listOf(
            LiveStream(1, "Feeding Block A Cats", "Bryan Addams", 150),
            LiveStream(2, "Karawaci Feeding", "NIKI", 50),
            LiveStream(3, "Karawaci Feeding", "Rick Price", 50)
        ),
        impactStats = listOf(
            ImpactStat("60", "Cats Fed"),
            ImpactStat("32", "Locations"),
            ImpactStat("20", "Cats Fed", highlight = true)
        ),
        feedPosts = listOf(
            FeedPost(
                id = 1,
                author = "Gatton",
                timeAgo = "7 mins ago",
                content = "Found a small ginger kitten at Benton Junction." + "Just fed her some wet food."
            )
        )
    )
    override suspend fun getDashboard(): Result<DashboardData> = runCatching {
        delay(800)
        // TODO(BACKEND): datanya dibutuhkan dari API
        //   GET /users/me                    -> { name, title, avatarUrl }
        //   GET /streams/live                -> [{ id, title, host, watching, thumbnailUrl }]
        //   GET /users/me/impact?period=month -> [{ value, label }]
        //   GET /feed/recent                 -> [{ id, author{name, avatarUrl}, createdAt, content, imageUrl }]
        sampleData
    }
}