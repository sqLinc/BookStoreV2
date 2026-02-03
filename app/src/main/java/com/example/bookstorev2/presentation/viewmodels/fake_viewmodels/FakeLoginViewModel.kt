package com.example.bookstorev2.presentation.viewmodels.fake_viewmodels

import android.content.Context
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.presentation.ui.state.LoginUiState
import com.example.bookstorev2.presentation.viewmodels.contracts.LoginViewModelContract
import com.google.firebase.auth.FirebaseUser

class FakeLoginViewModel : LoginViewModelContract {

    private val _uiState = mutableStateOf(LoginUiState())
    override val uiState: State<LoginUiState> = _uiState

    var loginClicked = false
    var registerClicked = false
    var googleClicked = false

    var loginResult: Result<User>? = null
    var registerResult: Result<User>? = null

    override fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email
        )
        clearError()
    }

    override fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password
        )
        clearError()
    }

    override fun onLoginClick() {
        loginClicked = true
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
        loginResult?.fold(
            onSuccess = { user ->
                _uiState.value = _uiState.value.copy(
                    user = user,
                    isLoading = false
                )
            },
            onFailure = { error ->
                _uiState.value = _uiState.value.copy(
                    error = error.message,
                    isLoading = false
                )
            }
        )
    }

    override fun onRegisterClick() {
        registerClicked = true
        _uiState.value = _uiState.value.copy(
            isLoading = true
        )
        registerResult?.fold(
            onSuccess = { user ->
                _uiState.value = _uiState.value.copy(
                    user = user,
                    isLoading = false
                )
            },
            onFailure = { error ->
                _uiState.value = _uiState.value.copy(
                    error = error.message,
                    isLoading = false
                )
            }
        )
    }

    override fun clearError() {
        _uiState.value = _uiState.value.copy(
            error = null
        )
    }

    override fun onLoginWithGoogleClick(context: Context, clientId: String) {
        googleClicked = true
        _uiState.value = _uiState.value.copy(
            isGoogle = true
        )
    }

    override fun onGoogleSuccess(user: FirebaseUser) {
        _uiState.value = _uiState.value.copy(
            user = User(user.uid, user.email ?: "")
        )
    }

    override fun loginLauncher(result: ActivityResult) {
        // noop
    }

}