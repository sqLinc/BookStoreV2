package com.example.bookstorev2.presentation.ui.state

import com.example.bookstorev2.domain.models.Book

data class BookListUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val category: String = "All",
    val isAdmin: Boolean = false
)