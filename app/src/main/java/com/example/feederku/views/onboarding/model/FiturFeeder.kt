package com.example.feederku.views.onboarding.model

import com.example.feederku.R

data class FiturFeeder(
    val id: Int,
    val nama: String,
    val resId: Int,
    val desc: String
)

val fiturFeeder = listOf(
    FiturFeeder(id = 1, nama = "Active Feeding", resId = R.drawable.vector_paw, desc = "Report sighted strays and log when you feed them."),
    FiturFeeder(id = 2, nama = "Emphatetic Donations", resId = R.drawable.vector_love, desc = "Support local stray rescue & medical campaigns."),
    FiturFeeder(id = 3, nama = "Community Exploration", resId = R.drawable.vector_location, desc = "Connect with certified feeders in your city area."),
)