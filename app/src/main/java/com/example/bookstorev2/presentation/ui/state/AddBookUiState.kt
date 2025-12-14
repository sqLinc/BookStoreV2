package com.example.bookstorev2.presentation.ui.state

import android.net.Uri

data class AddBookUiState(
    val key: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val category: String = "",
    val description: String = "",
    val price: String = "",
    val date: String = "",
    val author: String = "",
    val expanded: Boolean = false,
    var selectedImageUri: Uri? = null,
    val isFavorite: Boolean = false,
    val isRead: Boolean = false,
    val navigationEvent: MainAddScreenNavigation? = null,
    val error: String = "",
    val editKey: String = "",
    val isEditing: Boolean = false


)

