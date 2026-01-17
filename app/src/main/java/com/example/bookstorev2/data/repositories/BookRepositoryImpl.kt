package com.example.bookstorev2.data.repositories

import android.util.Log
import com.example.bookstorev2.data.local.room.BookFirebaseDataSource
import com.example.bookstorev2.data.local.room.BookRoomDataSource
import com.example.bookstorev2.data.local.room.mapper.bookListToDb
import com.example.bookstorev2.data.local.room.mapper.dtoListToDb
import com.example.bookstorev2.data.local.room.mapper.toDb
import com.example.bookstorev2.data.local.room.mapper.toDomain
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.presentation.navigation.onSavedSuccess
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BookRepositoryImpl @Inject constructor(
    private val room: BookRoomDataSource,
    private val firebase: BookFirebaseDataSource

) : BookRepository {


    override suspend fun getAllBooks(): List<Book> {
        Log.d("Room", "bookRepo.getAllBooks is working...")
        try {
            val remote = firebase.getBooks()
            Log.d("Room", "SUCCESS FROM REMOTE")
            room.saveAll(remote.dtoListToDb())
            Log.d("Room", "SUCCESS FROM SAVE ALL")
            val local = room.getBooks()
            Log.d("Room", "SUCCESS FROM LOCAL")
            return local.toDomain()


        } catch (e: Exception){
            Log.d("Room", "bookRepo.getAllBooks is FAILED")
            throw e
        }
    }

    override suspend fun isFavorite(bookId: String): Boolean {
        return firebase.isFavorite(bookId)
    }

    override suspend fun isRead(bookId: String): Boolean {
        return firebase.isRead(bookId)
    }

    override suspend fun setFavoriteStatus(bookId: String, isFavorite: Boolean) {
        firebase.setFavoriteStatus(bookId, isFavorite)
    }

    override suspend fun setReadStatus(bookId: String, isRead: Boolean) {
        firebase.setReadStatus(bookId, isRead)
    }

    override suspend fun saveBook(book: Book): Result<onSavedSuccess> {
        return firebase.saveBook(book)
    }


    override suspend fun getBookById(bookId: String): Book {
        val remote = firebase.getBookById(bookId)
        return remote.toDomain()
    }

    override suspend fun getFavBooks() : List<Book>{
        val remote = firebase.getFavBooks()
        room.saveAll(remote.dtoListToDb())
        val local = room.getFavBooks()
        return local.toDomain()
    }

    override suspend fun getReadBooks() : List<Book>{
        val remote = firebase.getReadBooks()
        room.saveAll(remote.dtoListToDb())
        val local = room.getReadBooks()
        return local.toDomain()

    }

    override suspend fun getBooksByCategory(category: String) : List<Book>{
        val remote = firebase.getBooksByCategory(category)
        room.saveAll(remote.dtoListToDb())
        val local = room.getBooksByCategory(category)
        return local.toDomain()
    }




}

