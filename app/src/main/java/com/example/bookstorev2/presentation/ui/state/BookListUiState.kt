package com.example.bookstorev2.presentation.ui.state

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import com.example.bookstorev2.domain.models.Book

data class BookListUiState (
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val navigationEvent: MainToAddScreenNav? = null,
    val category: String = ""
)