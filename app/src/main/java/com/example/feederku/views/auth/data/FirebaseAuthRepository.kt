package com.example.feederku.views.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser
    
    override suspend fun signInWithEmail(email: String, password: String) = runCatching {
        auth.signInWithEmailAndPassword(email, password).await()
        Unit
    }

    override suspend fun signUpWithEmail(name: String, email: String, password: String) = runCatching {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        result.user?.updateProfile(profileUpdates)?.await()
        Unit
    }

    override suspend fun signInWithGoogle(idToken: String) = runCatching {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).await()
        Unit
    }

    override fun signOut() {
        auth.signOut()
    }
}
