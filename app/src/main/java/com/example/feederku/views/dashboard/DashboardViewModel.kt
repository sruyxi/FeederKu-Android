package com.example.feederku.views.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.feederku.views.dashboard.data.DashboardRepository
import com.example.feederku.views.dashboard.data.FakeDashboardRepository
import com.example.feederku.views.dashboard.model.DashboardTab
import com.example.feederku.views.dashboard.model.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: DashboardRepository
) : ViewModel() {

    companion object {
        val Factory = viewModelFactory {
            initializer { DashboardViewModel(FakeDashboardRepository) } // ubah jadi repo asli nanti
        }
    }

    private val _uiState = MutableStateFlow(DashboardUiState(isLoading = true))
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            repository.getDashboard()
                .onSuccess { data ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            userName = data.userName,
                            userTitle = data.userTitle,
                            liveStream = data.liveStream,
                            impactStats = data.impactStats,
                            feedPosts = data.feedPosts
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = e.message ?: "Gagal memuat data")
                    }
                }
        }
    }

    fun onSearchQueryChange(value: String){
        _uiState.update { it.copy(searchQuery = value) }
        // TODO(BACKEND): GET /locations/search?q=...
    }

    fun onTabSelected(tab: DashboardTab){
        _uiState.update { it.copy(selectedTab = tab) }
    }
}