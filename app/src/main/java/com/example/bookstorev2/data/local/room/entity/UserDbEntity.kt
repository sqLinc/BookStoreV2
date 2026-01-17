package com.example.bookstorev2.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val email: String
)