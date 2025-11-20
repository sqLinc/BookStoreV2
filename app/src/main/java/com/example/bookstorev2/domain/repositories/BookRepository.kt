package com.example.bookstorev2.domain.repositories

import com.example.bookstorev2.domain.models.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun setFavoriteStatus(bookId: String, isFavorite: Boolean)
    suspend fun setReadStatus(bookId: String, isRead: Boolean)
    suspend fun saveBook(book: Book)

    suspend fun getAllBooks(): Flow<List<Book>>

    suspend fun isFavorite(bookId: String) : Boolean
}