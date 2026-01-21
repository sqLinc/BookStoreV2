package com.example.bookstorev2.data.local.room.dao

import android.R
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.bookstorev2.data.local.room.dto.BookDto
import com.example.bookstorev2.data.local.room.entity.BookDbEntity
import com.example.bookstorev2.domain.models.Book

@Dao
interface BookDao{

    @Upsert(entity = BookDbEntity::class)
    suspend fun insertNewBook(book: BookDbEntity)

    @Query("SELECT * FROM book")
    suspend fun getAllBooks() : List<BookDbEntity>

    @Query("DELETE FROM book WHERE `key` = :bookId")
    suspend fun deleteBookById(bookId: String)

    @Upsert
    suspend fun insertAllBooks(books: List<BookDbEntity>)

    @Query("SELECT * FROM book WHERE `key` = :bookId LIMIT 1")
    suspend fun getBookById(bookId: String) : BookDbEntity

    @Query("SELECT * FROM book WHERE favorite = 1")
    suspend fun getFavBooks() : List<BookDbEntity>

    @Query("SELECT * FROM book WHERE read = 1")
    suspend fun getReadBooks() : List<BookDbEntity>

    @Query("SELECT * FROM book WHERE category = :category")
    suspend fun getBooksByCategory(category: String) : List<BookDbEntity>

}