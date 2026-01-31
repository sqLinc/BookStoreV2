package com.example.bookstorev2.di

import com.example.bookstorev2.data.local.room.database.AppDataBase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DatabaseEntryPoint {
    fun appDatabase(): AppDataBase
}