package com.example.bookstorev2.data.local.room.dto

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class BookDto(
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
    val selectedImage: String = ""
)
