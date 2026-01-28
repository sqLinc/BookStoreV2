package com.example.bookstorev2.presentation.ui.state

import com.example.bookstorev2.domain.models.User
import com.google.android.gms.auth.api.signin.GoogleSignInClient

data class LoginUiState (
    val uid: String =  "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: User? = null,
    val isAdminState: Boolean = false,
    val contract: GoogleSignInClient? = null,
    val isGoogle: Boolean = false
)

