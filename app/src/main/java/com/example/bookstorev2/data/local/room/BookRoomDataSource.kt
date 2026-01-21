package com.example.bookstorev2.data.local.room

import android.util.Log
import com.example.bookstorev2.data.local.room.dao.BookDao
import com.example.bookstorev2.data.local.room.entity.BookDbEntity
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.presentation.navigation.onSavedSuccess
import javax.inject.Inject

class BookRoomDataSource @Inject constructor(
    private val dao: BookDao
)  {

    suspend fun getBooks() : List<BookDbEntity> {
        return dao.getAllBooks()
    }

    suspend fun saveBook(book: BookDbEntity) : Result<onSavedSuccess>{
        return try {
            dao.insertNewBook(book)
            Result.success(
                onSavedSuccess(
                    book.key
                )
            )
        } catch (e: Exception){
            throw e
        }

    }

    suspend fun deleteBook(bookId: String){
        dao.deleteBookById(bookId)
    }

    suspend fun saveAll(books: List<BookDbEntity>){
        dao.insertAllBooks(books)
    }

    suspend fun getBookById(bookId: String) : BookDbEntity {
        return dao.getBookById(bookId)
    }

    suspend fun getFavBooks() : List<BookDbEntity> {
        return dao.getFavBooks()
    }

    suspend fun getReadBooks() : List<BookDbEntity> {
        return dao.getReadBooks()
    }

    suspend fun getBooksByCategory(category: String) : List<BookDbEntity>{
        return dao.getBooksByCategory(category)
    }
}