package com.example.feederku.views.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.feederku.views.auth.data.AuthRepository
import com.example.feederku.views.auth.data.FakeAuthRepository
import com.example.feederku.views.auth.model.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
): ViewModel() {

    companion object {
        val Factory = viewModelFactory {
            initializer { AuthViewModel(FakeAuthRepository) }
        }
    }

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value) }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value.trim()) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun onLoginClick() = submit {
        repository.signInWithEmail(_uiState.value.email, _uiState.value.password)
    }

    fun onSignUpClick() = submit(
        onSuccess = { it.copy(isSignUpSuccess = true, isAuthenticated = false) }
    ) {
        with(_uiState.value) { repository.signUpWithEmail(name, email, password) }
    }

    fun onGoogleIdToken(idToken: String) = submit {
        repository.signInWithGoogle(idToken)
    }

    fun onGoogleError(message: String?) =
        _uiState.update {
            it.copy(isLoading = false, errorMessage = message ?: "Login Google gagal")
        }
    private fun submit(
        onSuccess: (AuthUiState) -> AuthUiState = { it.copy(isAuthenticated = true) },
        action: suspend () -> Result<Unit>
    ) {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            action()
                .onSuccess {
                    _uiState.update { s -> onSuccess(s).copy(isLoading = false) }
                }
                .onFailure { e ->
                    _uiState.update { s ->
                        s.copy(isLoading = false, errorMessage = e.message ?: "Terjadi kesalahan")
                    }
                }
        }
    }
}