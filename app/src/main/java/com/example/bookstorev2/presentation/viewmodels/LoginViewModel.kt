package com.example.bookstorev2.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

) : ViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    fun onEmailChange(email: String){
        _uiState.value = _uiState.value.copy(email = email)
    }
    fun onPasswordChange(password: String){
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onLoginClick() {
         viewModelScope.launch {

                val result = authByEmailPassUseCase(
                    _uiState.value.email,
                    _uiState.value.password,
                )
             if(result.isSuccess){
                 val userData = result.getOrNull()!!
                 _uiState.value = _uiState.value.copy(
                     navigationEvent = NavigationEvent.NavigateToMainScreen(userData)
                 )
             }

        }

    }

    fun onRegisterClick(){
        viewModelScope.launch {


                val result = registerByEmailPassUseCase(
                    _uiState.value.email,
                    _uiState.value.password
                )
            if(result.isSuccess){
                val userData = result.getOrNull()!!
                _uiState.value = _uiState.value.copy(
                    navigationEvent = NavigationEvent.NavigateToMainScreen(userData)
                )
            }





        }
    }

    fun clearError(){
        _uiState.value = _uiState.value.copy(error = null)
    }
    fun onNavigationConsumed() {
        _uiState.value = _uiState.value.copy(navigationEvent = null)
    }


}