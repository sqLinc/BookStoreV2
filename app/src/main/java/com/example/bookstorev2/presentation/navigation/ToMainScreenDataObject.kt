package com.example.bookstorev2.presentation.navigation

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ToMainScreenDataObject (
    val uid: String = "",
    val email: String = ""
)