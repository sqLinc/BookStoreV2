package com.example.bookstorev2.presentation.navigation

sealed class LoginMainNavigation {
    data class NavigateToMainScreen(val userData: ToMainScreenDataObject) : LoginMainNavigation()
}