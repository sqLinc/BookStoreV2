package com.example.bookstorev2.domain.models

data class Book(
    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val category: String = "",
    val description: String = "",
    val price: String = "",
    val date: String = "",
    val author: String = "",
    val isFavorite: Boolean = false,
    val isRead: Boolean = false
)