package com.example.bookstorev2.presentation.viewmodels.contracts

import androidx.activity.result.ActivityResult
import androidx.compose.runtime.State
import com.example.bookstorev2.presentation.ui.state.LoginUiState
import com.google.firebase.auth.FirebaseUser
import io.grpc.Context

interface LoginViewModelContract {
    val uiState: State<LoginUiState>

    fun onEmailChange(email: String)
    fun onPasswordChange(password: String)
    fun onLoginClick()
    fun onRegisterClick()
    fun clearError()
    fun onLoginWithGoogleClick(context: android.content.Context, clientId: String)
    fun onGoogleSuccess(user: FirebaseUser)
    fun loginLauncher(result: ActivityResult)
}