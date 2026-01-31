package com.example.bookstorev2.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookDbEntity(
    @PrimaryKey val key: String = "",
    val title: String = "",
    @ColumnInfo(name = "base_64_image") val base64Image: String = "",
    val category: String = "",
    val description: String = "",
    val price: String = "",
    val date: String = "",
    val author: String = "",
    val favorite: Boolean = false,
    val read: Boolean = false,
    @ColumnInfo(name = "image_uri") val imageUri: String = ""
)
