package com.example.bookstorev2.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookstorev2.data.local.room.dao.BookDao
import com.example.bookstorev2.data.local.room.dao.UserDao
import com.example.bookstorev2.data.local.room.entity.BookDbEntity
import com.example.bookstorev2.data.local.room.entity.UserDbEntity

@Database(
    version = 1,
    entities = [
        UserDbEntity::class,
        BookDbEntity::class
    ]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getUserDao() : UserDao
    abstract fun bookDao() : BookDao
}