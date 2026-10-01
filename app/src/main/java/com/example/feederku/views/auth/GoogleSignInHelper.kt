package com.example.feederku.views.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.CustomCredential
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.example.feederku.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

suspend fun getGoogleIdToken(
    context: Context,
): Result<String> = try {
    val option = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(context.getString(R.string.google_web_client_id))
        .build()
    val request = GetCredentialRequest.Builder().addCredentialOption(option).build()
    val cred = CredentialManager.create(context).getCredential(context, request).credential
    if (cred is CustomCredential &&
        cred.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
    ) {
        Result.success(GoogleIdTokenCredential.createFrom(cred.data).idToken)
    } else {
        Result.failure(Exception("Credential bukan Google ID token"))
    }
} catch (e: GetCredentialCancellationException) {
    Result.failure(Exception("Login dibatalkan"))
} catch (e: Exception) {
    Result.failure(e)
}