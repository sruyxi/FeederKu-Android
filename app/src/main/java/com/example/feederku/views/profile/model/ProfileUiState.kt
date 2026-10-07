package com.example.feederku.views.profile.model

data class ProfileUiState(
    val user: ProfileUser,
    val selectedTab: Int = 0, //nyimpan tab yg lagi dipilih
    val feedingHistory: List<FeedingHistory> = emptyList(),
    val donationHistory: List<DonationHistory> = emptyList()
)