package com.example.bookstorev2.domain.repositories

import com.example.bookstorev2.data.local.room.dao.BookDao
import com.example.bookstorev2.data.local.room.dto.BookDto
import com.example.bookstorev2.data.local.room.entity.BookDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RoomBookRepository(private val bookDao: BookDao) {

    suspend fun insertNewBook(bookDbEntity: BookDbEntity) {
        withContext(Dispatchers.IO) {
            bookDao.insertNewBook(bookDbEntity)
        }
    }

    suspend fun getAllBooks() : List<BookDbEntity> {
        return withContext(Dispatchers.IO) {
            return@withContext bookDao.getAllBooks()
        }
    }

    suspend fun deleteBookById(bookId: String) {
        withContext(Dispatchers.IO) {
            bookDao.deleteBookById(bookId)
        }
    }

}