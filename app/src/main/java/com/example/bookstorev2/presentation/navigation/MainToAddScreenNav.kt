package com.example.bookstorev2.presentation.navigation

sealed class MainToAddScreenNav {
    data class NavigateOnEdit(val key: onEdit) : MainToAddScreenNav()
}