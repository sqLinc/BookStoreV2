package com.example.bookstorev2.presentation.ui.state

import com.example.bookstorev2.presentation.navigation.onEdit
import com.example.bookstorev2.presentation.navigation.onSavedSuccess

sealed class MainToAddScreenNav {
    data class NavigateOnEdit(val key: onEdit) : MainToAddScreenNav()
}