package com.example.bookstorev2.data.local.room.dto

data class BookDto(
    val key: String = "",
    val title: String = "",
    val base64Image: String = "",
    val category: String = "",
    val description: String = "",
    val price: String = "",
    val date: String = "",
    val author: String = "",
    val favorite: Boolean = false,
    val read: Boolean = false,
    val imageUri: String = ""
)
