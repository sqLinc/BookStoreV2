package com.example.bookstorev2.data.local.room.dto

import androidx.room.ColumnInfo


data class UserDto(
    val id: Long,
    @ColumnInfo(name = "email") val email: String
)
