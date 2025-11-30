package com.example.bookstorev2.presentation.ui.state

import com.example.bookstorev2.presentation.navigation.ToMainScreenDataObject

data class LoginUiState (
    val uid: String =  "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val navigationEvent: NavigationEvent? = null
)

sealed class NavigationEvent {
    data class NavigateToMainScreen(val userData: ToMainScreenDataObject) : NavigationEvent()

}