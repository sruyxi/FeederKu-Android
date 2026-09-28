package com.example.feederku.views.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class OnBoardingUiState(
    val currentPage: Int = 1
){
    val isLastPage: Boolean get() = currentPage == TOTAL_PAGES

    companion object {
        const val TOTAL_PAGES = 3
    }
}
class OnBoardingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OnBoardingUiState())
    val uiState: StateFlow<OnBoardingUiState> = _uiState.asStateFlow()

    fun onNextClicked() {
        _uiState.update { state ->
            if (state.isLastPage) state
            else state.copy(currentPage = state.currentPage + 1)
        }
    }
}