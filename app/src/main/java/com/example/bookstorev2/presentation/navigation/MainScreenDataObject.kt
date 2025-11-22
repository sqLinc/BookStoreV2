package com.example.bookstorev2.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data class MainScreenDataObject (
    val uid: String = "",
    val email: String = ""
)