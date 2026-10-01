package com.example.feederku.views.auth.model

data class AuthUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAuthenticated: Boolean = false,
    val isSignUpSuccess: Boolean= false
){
    val isNameValid: Boolean
        get() = name.isNotBlank()
    val isEmailValid: Boolean
        get() = EMAIL_REGEX.matches(email)
    val isPasswordValid: Boolean
        get() = password.length >= MIN_PASSWORD_LENGTH
    val canSignUp: Boolean
        get() = isNameValid && isEmailValid && isPasswordValid && !isLoading
    val canLogin: Boolean
        get() = isEmailValid && isPasswordValid && !isLoading

    companion object{
        const val MIN_PASSWORD_LENGTH = 8
        private val EMAIL_REGEX= "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$".toRegex()
    }
}