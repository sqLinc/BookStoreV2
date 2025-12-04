package com.example.bookstorev2.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.data.repositories.UserRepositoryImpl
import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.domain.usecases.AuthByEmailPassUseCase
import com.example.bookstorev2.domain.usecases.RegisterByEmailPassUseCase
import com.example.bookstorev2.presentation.ui.state.LoginUiState
import com.example.bookstorev2.presentation.ui.state.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authByEmailPassUseCase: AuthByEmailPassUseCase,
    private val registerByEmailPassUseCase: RegisterByEmailPassUseCase,
    private val userRepo: UserRepository

) : ViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState


    suspend fun onAdminCheck(){
        val isAdmin = userRepo.isAdmin()
        if (isAdmin){
            _uiState.value = _uiState.value.copy(
                isAdminState = isAdmin
            )
        }

    }

    fun onEmailChange(email: String){
        _uiState.value = _uiState.value.copy(email = email)
        clearError()
    }
    fun onPasswordChange(password: String){
        _uiState.value = _uiState.value.copy(password = password)
        clearError()
    }

    fun onLoginClick() {
         viewModelScope.launch {


                 val result = authByEmailPassUseCase(
                     _uiState.value.email,
                     _uiState.value.password,
                 )
             
                result.fold(
                    onSuccess = { userData ->
                        _uiState.value = _uiState.value.copy(
                            navigationEvent = NavigationEvent.NavigateToMainScreen(userData)
                        )

                    },
                    onFailure = { e->
                        _uiState.value = _uiState.value.copy(
                            error = e.message ?: "Auth error"
                        )
                    }
                )

        }

    }

    fun onRegisterClick(){
        viewModelScope.launch {


                val result = registerByEmailPassUseCase(
                    _uiState.value.email,
                    _uiState.value.password
                )
            result.fold(
                onSuccess = { userData ->
                    _uiState.value = _uiState.value.copy(
                        navigationEvent = NavigationEvent.NavigateToMainScreen(userData)
                    )
                },
                onFailure = { e->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Register error"
                    )
                }
            )





        }
    }

    fun clearError(){
        _uiState.value = _uiState.value.copy(error = null)
    }
    fun onNavigationConsumed() {
        _uiState.value = _uiState.value.copy(navigationEvent = null)
    }




}