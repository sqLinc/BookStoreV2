package com.example.bookstorev2.presentation.ui.state

import com.example.bookstorev2.domain.models.User

data class LoginUiState (
    val uid: String =  "",
    val email: String = "darwinjohansen06@gmail.com",
    val password: String = "12345678",
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: User? = null,
    val isAdminState: Boolean = false
)

