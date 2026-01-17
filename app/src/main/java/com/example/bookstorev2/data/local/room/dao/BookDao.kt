package com.example.bookstorev2.data.local.room.dao

import android.R
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookstorev2.data.local.room.dto.BookDto
import com.example.bookstorev2.data.local.room.dto.UserDto
import com.example.bookstorev2.data.local.room.entity.BookDbEntity
import com.example.bookstorev2.data.local.room.entity.UserDbEntity

@Dao
interface BookDao{

    @Insert(entity = BookDbEntity::class)
    fun insertNewBook(book: BookDbEntity)

    @Query("SELECT * FROM book")
    suspend fun getAllBooks() : List<BookDbEntity>

    @Query("DELETE FROM book WHERE `key` = :bookId")
    fun deleteBookById(bookId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBooks(books: List<BookDbEntity>)

    @Query("SELECT * FROM book WHERE `key` = :bookId LIMIT 1")
    fun getBookById(bookId: String) : BookDbEntity

    @Query("SELECT * FROM book WHERE favorite = 1")
    fun getFavBooks() : List<BookDbEntity>

    @Query("SELECT * FROM book WHERE read = 1")
    fun getReadBooks() : List<BookDbEntity>

    @Query("SELECT * FROM book WHERE category = :category")
    fun getBooksByCategory(category: String) : List<BookDbEntity>
}