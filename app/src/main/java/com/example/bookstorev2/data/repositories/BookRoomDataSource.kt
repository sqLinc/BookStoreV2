package com.example.bookstorev2.data.repositories

import android.util.Log
import com.example.bookstorev2.data.local.room.dao.BookDao
import com.example.bookstorev2.data.local.room.entity.BookDbEntity
import javax.inject.Inject

class BookRoomDataSource @Inject constructor(
    private val dao: BookDao
) {

    suspend fun getBooks(): List<BookDbEntity> {
        return dao.getAllBooks()
    }

    suspend fun saveAll(books: List<BookDbEntity>) {
        Log.d("MyLog", "saveAll RoomData...")
        dao.insertAllBooks(books)
    }

    suspend fun getBookById(bookId: String): BookDbEntity {
        return dao.getBookById(bookId)
    }

    suspend fun getFavBooks(): List<BookDbEntity> {
        return dao.getFavBooks()
    }

    suspend fun getReadBooks(): List<BookDbEntity> {
        return dao.getReadBooks()
    }

    suspend fun getBooksByCategory(category: String): List<BookDbEntity> {
        return dao.getBooksByCategory(category)
    }

    suspend fun updatedBook(book: BookDbEntity) {
        dao.insertNewBook(book)
    }

    suspend fun deleteAllFromLocal() {
        dao.deleteAllFromLocal()
    }

}