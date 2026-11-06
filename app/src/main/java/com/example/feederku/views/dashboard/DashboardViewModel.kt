package com.example.feederku.views.dashboard

import androidx.lifecycle.ViewModel
import com.example.feederku.repository.DashboardRepository
import com.example.feederku.views.dashboard.model.DashboardTab
import com.example.feederku.views.dashboard.model.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel(
    private val repository: DashboardRepository = DashboardRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        fetchDashboardData()
    }

    fun fetchDashboardData() {
        _uiState.update { it.copy(isLoading = true) }
        try {
            _uiState.update { currentState ->
                currentState.copy(
                    userName = "Sarah",
                    userTitle = "Certified Stray Protector",
                    recentStreams = repository.getRecentLiveStreams(),
                    impactStats = repository.getImpactStats(),
                    feedPosts = listOf(repository.getRecentFeed()),
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "Terjadi kesalahan")
            }
        }
    }

    fun onSearchQueryChange(value: String) {
        _uiState.update { it.copy(searchQuery = value) }
    }

    fun onTabSelected(tab: DashboardTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }
}
