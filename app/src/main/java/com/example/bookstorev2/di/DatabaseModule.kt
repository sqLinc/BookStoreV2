package com.example.bookstorev2.di

import android.content.Context
import androidx.room.Room
import com.example.bookstorev2.data.local.room.database.AppDataBase

object DatabaseModule {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }
    private val appDataBase: AppDataBase by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, "database.db")
            .build()
    }
}