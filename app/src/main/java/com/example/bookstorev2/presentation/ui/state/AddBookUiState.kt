package com.example.bookstorev2.presentation.ui.state

import android.net.Uri
import com.example.bookstorev2.domain.models.Book

data class AddBookUiState(
    val key: String = "",
    val title: String = "",
    val base64Image: String = "",
    val category: String = "",
    val description: String = "",
    val price: String = "",
    val date: String = "",
    val author: String = "",
    var imageUri: Uri? = null,
    val isFavorite: Boolean = false,
    val isRead: Boolean = false,
    val savedBook: Book? = null,
    val error: String = "",


)

