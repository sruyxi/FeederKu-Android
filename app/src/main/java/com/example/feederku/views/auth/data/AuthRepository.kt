package com.example.feederku.views.auth.data

import kotlinx.coroutines.delay

interface AuthRepository{
    suspend fun signInWithEmail(email: String, password: String): Result<Unit>
    suspend fun signUpWithEmail(name: String, email: String, password: String): Result<Unit>
    suspend fun signInWithGoogle(idToken: String): Result<Unit>
}

object FakeAuthRepository : AuthRepository {
    private data class FakeUser(val name: String, val password: String)
    private val users = mutableMapOf<String, FakeUser>() //nyimpan sementara di memori

    override suspend fun signInWithEmail(email: String, password: String) = runCatching {
        delay(1000)
        //TODO (BACKEND)
        val user = users[email.lowercase()]
            ?: error("Akun belum terdaftar. Silakan Sign Up terlebih dahulu")
        if (user.password != password) error ("Email atau password Anda salah")
        Unit
    }
    override suspend fun signUpWithEmail(name: String, email: String, password: String) = runCatching{
        delay(1000)
        //TODO (BACKEND)
        val key = email.lowercase()
        if(users.containsKey(key)) error ("Email sudah terdaftar. Silakan Login kembali")
        users[key] = FakeUser(name, password)
        Unit
    }
    override suspend fun signInWithGoogle(idToken: String) = runCatching{
        delay(1000)
        //TODO (BACKEND)
        Unit
    }
}