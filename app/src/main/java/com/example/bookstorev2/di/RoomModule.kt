package com.example.bookstorev2.di

import android.content.Context
import androidx.room.Room
import com.example.bookstorev2.data.local.room.BookRoomDataSource
import com.example.bookstorev2.data.local.room.dao.BookDao
import com.example.bookstorev2.data.local.room.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDataBase =
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "database.db"
        ).build()

    @Provides
    fun provideBookDao(db: AppDataBase): BookDao = db.bookDao()

    @Provides
    fun provideBookRoomDataSource(
        dao: BookDao
    ): BookRoomDataSource =
        BookRoomDataSource(dao)
}

