package com.example.feederku.views.profile.model

import androidx.annotation.DrawableRes

enum class DonationStatus(val label: String){
    COMPLETED("Completed"),
    ON_GOING("On Going")
}

data class FeedingHistory(
    val location: String,
    val description: String,
    val time: String,
    @DrawableRes val imageRes: Int
)

data class DonationHistory(
    val title: String,
    val amount: String,
    val status: DonationStatus,
    val date: String,
    @DrawableRes val imageRes: Int
)

data class ProfileUser(
    val name: String,
    val username: String,
    val bio: String,
    val catsFed: Int,
    val locations: Int,
    val catsAdopted: Int,
    @DrawableRes val imageRes: Int
)