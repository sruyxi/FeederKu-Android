package com.example.feederku.repository

import com.example.feederku.model.FeedPost
import com.example.feederku.model.ImpactStat
import com.example.feederku.model.LiveStream

class DashboardRepository {
    fun getRecentLiveStreams(): List<LiveStream> {
        return listOf(
            LiveStream("1", "Feeding Block B Cats", "John Doe", 142),
            LiveStream("2", "Karawaci Feeding", "Joce", 40)
        )
    }

    fun getImpactStats(): ImpactStat {
        return ImpactStat(catsFed = 89, locations = 12, activeVolunteers = 10)
    }

    fun getRecentFeed(): FeedPost {
        return FeedPost(
            id = "post_1",
            authorName = "Budi Utomo",
            timeAgo = "10 mins ago",
            content = "Found a small ginger kitten under the bridge at Block C. Just fed her some dry food and water. She seems playful!"
        )
    }
}