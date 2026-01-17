package com.example.bookstorev2.presentation.ui.state

import com.example.bookstorev2.presentation.navigation.onEdit

sealed class MainToAddScreenNav {
    data class NavigateOnEdit(val key: onEdit) : MainToAddScreenNav()
}