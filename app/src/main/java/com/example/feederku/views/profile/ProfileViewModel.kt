package com.example.feederku.views.profile

import androidx.lifecycle.ViewModel
import com.example.feederku.R
import com.example.feederku.views.profile.data.ProfileRepository
import com.example.feederku.views.profile.model.ProfileUiState
import com.example.feederku.views.profile.model.ProfileUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel (
    private val repository: ProfileRepository = ProfileRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            user = repository.getUser(),
            feedingHistory = repository.getFeedingHistory(),
            donationHistory = repository.getDonationHistory()
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun selectTab(index: Int){
        _uiState.update { it.copy(selectedTab = index) }
    }
}