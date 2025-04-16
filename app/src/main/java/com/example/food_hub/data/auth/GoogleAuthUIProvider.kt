package com.example.food_hub.data.auth

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.example.food_hub.GoogleServerClientId
import com.example.food_hub.data.models.GoogleAccount
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

class GoogleAuthUIProvider {
    suspend fun signIn(
        activityContext: Context, credentialManager: CredentialManager
    ): GoogleAccount? {
        try {
            val credential =
                credentialManager.getCredential(activityContext, getCredentialRequest())
            return handleCredentials(credential)
        } catch (e: GetCredentialException) {
            return null
        }
    }

    private fun handleCredentials(result: GetCredentialResponse): GoogleAccount {
        Log.d("Credential", "My result $result")
        when (val credential = result.credential) {
            is GoogleIdTokenCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = credential as GoogleIdTokenCredential
                    Log.d("GoogleAuthUiProvider", "GoogleIdTokenCredential: $googleIdTokenCredential")
                    return GoogleAccount(
                        token = googleIdTokenCredential.idToken,
                        displayName = googleIdTokenCredential.displayName ?: "",
                        profileImageUrl = googleIdTokenCredential.profilePictureUri.toString()
                    )
                } else {
                    Log.d("GoogleAuthUiProvider", "Unknown credential type: ${credential.type}")
                }
            }

            else -> {
                Log.d("GoogleAuthUiProvider", "Unknown credential: ${credential.type}")
            }
        }
        Log.d("GoogleAuthUiProvider", "Unknown credential failed")
        return GoogleAccount(
            token = "",
            displayName = "",
            profileImageUrl = ""
        )
    }

    private fun getCredentialRequest(): GetCredentialRequest {
        return GetCredentialRequest.Builder().addCredentialOption(
            GetSignInWithGoogleOption.Builder(
                GoogleServerClientId
            ).build()
        ).build()
    }
}