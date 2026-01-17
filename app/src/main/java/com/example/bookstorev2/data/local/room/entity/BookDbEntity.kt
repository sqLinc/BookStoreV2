package com.example.bookstorev2.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookDbEntity(
    @PrimaryKey val key: String = "",
    val title: String = "",
    @ColumnInfo(name = "image_url") val imageUrl: String = "",
    val category: String = "",
    val description: String = "",
    val price: String = "",
    val date: String = "",
    val author: String = "",
    val favorite: Boolean = false,
    val read: Boolean = false,
    @ColumnInfo(name = "selected_image") val selectedImage: String = ""
)
