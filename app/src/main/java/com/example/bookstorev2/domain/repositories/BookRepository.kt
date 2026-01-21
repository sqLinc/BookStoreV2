package com.example.bookstorev2.domain.repositories

import com.example.bookstorev2.data.local.room.entity.BookDbEntity
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.presentation.navigation.onSavedSuccess

interface BookRepository {
    suspend fun setFavoriteStatus(book: Book, isFavorite: Boolean, uid: String) : Boolean
    suspend fun setReadStatus(book: Book, isRead: Boolean, uid: String) : Boolean

    suspend fun saveBook(book: Book) : Result<Book>
    suspend fun getAllBooks(uid: String): List<Book>

    suspend fun isFavorite(bookId: String, uid: String) : Boolean?
    suspend fun isRead(bookId: String, uid: String) : Boolean?
    suspend fun getBookById(bookId: String) : Book
    suspend fun getFavBooks() : List<Book>
    suspend fun getReadBooks() : List<Book>
    suspend fun getBooksByCategory(category: String) : List<Book>
    suspend fun updateLocalBook(book: Book)



}