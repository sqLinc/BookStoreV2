package com.example.bookstorev2.domain.repositories


import com.example.bookstorev2.domain.models.Book

interface BookRepository {
    suspend fun setFavoriteStatus(bookId: String, isFavorite: Boolean, uid: String): Boolean
    suspend fun setReadStatus(bookId: String, isRead: Boolean, uid: String): Boolean
    suspend fun saveBook(book: Book): Result<Book>
    suspend fun getAllBooks(): List<Book>
    suspend fun isFavorite(bookId: String, uid: String): Boolean?
    suspend fun isRead(bookId: String, uid: String): Boolean?
    suspend fun getBookById(bookId: String): Book
    suspend fun getFavBooks(): List<Book>
    suspend fun getReadBooks(): List<Book>
    suspend fun getBooksByCategory(category: String): List<Book>
    suspend fun updateLocalBook(book: Book)
    suspend fun getFavIds(uid: String): List<String>
    suspend fun getReadIds(uid: String): List<String>
    suspend fun saveAllToLocal(books: List<Book>)
    suspend fun deleteAllFromLocal()


}