package com.example.bookstorev2.presentation.ui.state

import com.example.bookstorev2.presentation.navigation.onSavedSuccess

sealed class MainAddScreenNavigation {
        data class NavigateOnSaved(val key: onSavedSuccess) : MainAddScreenNavigation()
}