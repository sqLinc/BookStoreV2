package com.example.bookstorev2.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookstorev2.data.local.room.dao.BookDao
import com.example.bookstorev2.data.local.room.entity.BookDbEntity

@Database(
    version = 3,
    entities = [
        BookDbEntity::class
    ]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun bookDao() : BookDao
}