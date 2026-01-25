package com.example.bookstorev2.presentation.viewmodels

import android.util.Log
import androidx.browser.R
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.data.repositories.SettingsRepository
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.domain.usecases.AuthByEmailPassUseCase
import com.example.bookstorev2.domain.usecases.RegisterByEmailPassUseCase
import com.example.bookstorev2.presentation.ui.state.LoginMainNavigation
import com.example.bookstorev2.presentation.ui.state.LoginUiState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import io.grpc.Context
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authByEmailPassUseCase: AuthByEmailPassUseCase,
    private val registerByEmailPassUseCase: RegisterByEmailPassUseCase,
    private val userRepo: UserRepository,
    private val settingRepo: SettingsRepository

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

                 Log.d("appvm", "onLoginClick started...")
                 Log.d("appvm", "Incoming data: ${_uiState.value.email}, ${_uiState.value.password}")
                 val result = authByEmailPassUseCase(
                     _uiState.value.email,
                     _uiState.value.password,
                 )

             
                result.fold(
                    onSuccess = { userData ->
                        _uiState.value = _uiState.value.copy(
                            user = userData
                        )
                        settingRepo.setEmail(userData.email)
                        settingRepo.setUid(userData.uid)


                    },
                    onFailure = { e->
                        _uiState.value = _uiState.value.copy(
                            error = e.message ?: "Auth error"
                        )
                    }
                )
                Log.d("appvm", "Outcoming data: ${_uiState.value.user}")

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
                        user = userData
                    )
                    settingRepo.setEmail(userData.email)
                    settingRepo.setUid(userData.uid)
//
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

    fun getGoogleSignInClient(context: android.content.Context, string: String): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(string)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    fun onGoogleSuccess(user: FirebaseUser){
        _uiState.value = _uiState.value.copy(
            user = User(user.uid, user.email!!)
        )
        viewModelScope.launch {
            settingRepo.setEmail(user.email!!)
            settingRepo.setUid(user.uid)
        }
        Log.d("GoogleAuth", "Google success: " + _uiState.value.user.toString())

    }



}