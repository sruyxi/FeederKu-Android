package com.example.feederku.views.profile.data

import com.example.feederku.R
import com.example.feederku.views.profile.model.DonationHistory
import com.example.feederku.views.profile.model.DonationStatus
import com.example.feederku.views.profile.model.FeedingHistory
import com.example.feederku.views.profile.model.ProfileUser

class ProfileRepository {
    fun getUser() = ProfileUser(
        name = "Dhea",
        username = "@dhea",
        bio = "Animal lover | Jakarta stray feeder since 2023",
        catsFed = 50,
        locations = 23,
        catsAdopted = 5,
        imageRes = R.drawable.profile_circle
    )

    fun getFeedingHistory() = listOf(
        FeedingHistory("Benton Junction", "Fed 2 orange cats", "Today, 10.30 AM", R.drawable.profile_circle),
        FeedingHistory("UPH", "Fed 1 tabby cat", "Today, 07.15 AM", R.drawable.profile_circle),
    )

    fun getDonationHistory() = listOf(
        DonationHistory("Save the Beach District Cats", "150.000", DonationStatus.COMPLETED, "28 September 2026", R.drawable.profile_circle),
        DonationHistory("Medical Care", "100.000", DonationStatus.ON_GOING, "19 Maret 2026", R.drawable.profile_circle),
    )
}