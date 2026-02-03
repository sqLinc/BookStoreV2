package com.example.bookstorev2.presentation.viewmodels.viewmodels

import android.content.Context
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.data.repositories.SettingsRepository
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.domain.usecases.AuthByEmailPassUseCase
import com.example.bookstorev2.domain.usecases.RegisterByEmailPassUseCase
import com.example.bookstorev2.presentation.ui.state.LoginUiState
import com.example.bookstorev2.presentation.viewmodels.contracts.LoginViewModelContract
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authByEmailPassUseCase: AuthByEmailPassUseCase,
    private val registerByEmailPassUseCase: RegisterByEmailPassUseCase,
    private val userRepo: UserRepository,
    private val settingRepo: SettingsRepository,

    ) : ViewModel(), LoginViewModelContract {

    private val _uiState = mutableStateOf(LoginUiState())
    override val uiState: State<LoginUiState> = _uiState


    suspend fun onAdminCheck() {
        val isAdmin = userRepo.isAdmin()
        if (isAdmin) {
            _uiState.value = _uiState.value.copy(
                isAdminState = true
            )
        }

    }

    override fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
        clearError()
    }

    override fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
        clearError()
    }

    override fun onLoginClick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            val result = authByEmailPassUseCase(
                _uiState.value.email,
                _uiState.value.password,
            )


            result.fold(
                onSuccess = { userData ->
                    _uiState.value = _uiState.value.copy(
                        user = userData,
                        isLoading = false
                    )
                    settingRepo.setEmail(userData.email)
                    settingRepo.setUid(userData.uid)



                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Auth error",
                        isLoading = false
                    )

                }
            )

        }

    }

    override fun onRegisterClick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            val result = registerByEmailPassUseCase(
                _uiState.value.email,
                _uiState.value.password
            )
            result.fold(
                onSuccess = { userData ->
                    _uiState.value = _uiState.value.copy(
                        user = userData,
                        isLoading = false
                    )
                    settingRepo.setEmail(userData.email)
                    settingRepo.setUid(userData.uid)
//
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Register error",
                        isLoading = false
                    )
                }
            )

        }
    }

    override fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    override fun onLoginWithGoogleClick(context: Context, clientId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )
            val contract = userRepo.loginByGoogle(context, clientId)
            contract.fold(
                onSuccess = { result ->
                    _uiState.value = _uiState.value.copy(
                        contract = result,
                        isGoogle = true,
                        isLoading = false
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            )
        }

    }

    override fun onGoogleSuccess(user: FirebaseUser) {
        _uiState.value = _uiState.value.copy(
            user = User(user.uid, user.email!!)
        )
        viewModelScope.launch {
            settingRepo.setEmail(user.email!!)
            settingRepo.setUid(user.uid)
        }

    }

    override fun loginLauncher(result: ActivityResult) {
        viewModelScope.launch {
            val user = userRepo.googleLauncher(result)

            user.fold(
                onSuccess = { user ->
                    onGoogleSuccess(user)
                    _uiState.value = _uiState.value.copy(
                        isGoogle = false
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message
                    )
                }
            )
        }
    }

}