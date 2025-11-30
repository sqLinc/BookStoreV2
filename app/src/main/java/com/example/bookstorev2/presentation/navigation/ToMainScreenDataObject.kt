package com.example.bookstorev2.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data class ToMainScreenDataObject (
    val uid: String = "",
    val email: String = ""
)