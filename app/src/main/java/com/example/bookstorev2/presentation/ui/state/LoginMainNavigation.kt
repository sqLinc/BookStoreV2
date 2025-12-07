package com.example.bookstorev2.presentation.ui.state

import com.example.bookstorev2.presentation.navigation.ToMainScreenDataObject

sealed class LoginMainNavigation {
    data class NavigateToMainScreen(val userData: ToMainScreenDataObject) : LoginMainNavigation()
}