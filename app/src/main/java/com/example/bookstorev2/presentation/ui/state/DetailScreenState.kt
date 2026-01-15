package com.example.bookstorev2.presentation.ui.state

import com.example.bookstorev2.domain.models.Book

data class DetailScreenState(
    val key: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val category: String = "",
    val description: String = "",
    val price: String = "",
    val date: String = "",
    val author: String = "",
    val favorite: Boolean = false,
    val read: Boolean = false,
    val selectedImage: String = "",
    val error: String = ""

    )