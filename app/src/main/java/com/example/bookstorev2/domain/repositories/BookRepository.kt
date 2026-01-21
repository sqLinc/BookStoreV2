package com.example.bookstorev2.domain.repositories

import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.presentation.navigation.onSavedSuccess

interface BookRepository {
    suspend fun setFavoriteStatus(bookId: String, isFavorite: Boolean) : Boolean
    suspend fun setReadStatus(bookId: String, isRead: Boolean) : Boolean

    suspend fun saveBook(book: Book) : Result<Book>
    suspend fun getAllBooks(): List<Book>

    suspend fun isFavorite(bookId: String) : Boolean
    suspend fun isRead(bookId: String) : Boolean
    suspend fun getBookById(bookId: String) : Book
    suspend fun getFavBooks() : List<Book>
    suspend fun getReadBooks() : List<Book>
    suspend fun getBooksByCategory(category: String) : List<Book>



}