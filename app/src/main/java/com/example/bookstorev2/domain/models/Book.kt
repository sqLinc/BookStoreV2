package com.example.bookstorev2.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
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

) : Parcelable