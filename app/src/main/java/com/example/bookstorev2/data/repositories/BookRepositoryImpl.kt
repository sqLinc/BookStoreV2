package com.example.bookstorev2.data.repositories

import android.util.Log
import com.example.bookstorev2.data.repositories.BookFirebaseDataSource
import com.example.bookstorev2.data.local.room.BookRoomDataSource
import com.example.bookstorev2.data.local.room.mapper.bookListToDb
import com.example.bookstorev2.data.local.room.mapper.dbListToDomain
import com.example.bookstorev2.data.local.room.mapper.dtoListToDb
import com.example.bookstorev2.data.local.room.mapper.dtoListToDomain
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
        Log.d("Room", "TRYING TO GET BOOKS")
        try {
            val remote = firebase.getBooks()
            Log.d("Room", "GOT BOOKS FROM REMOTE")
            room.saveAll(remote.dtoListToDb())
            Log.d("Room", "SAVED BOOKS TO ROOM")
            return remote.dtoListToDomain()


        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun isFavorite(bookId: String): Boolean {
        return firebase.isFavorite(bookId)
    }

    override suspend fun isRead(bookId: String): Boolean {
        return firebase.isRead(bookId)
    }

    override suspend fun setFavoriteStatus(bookId: String, isFavorite: Boolean) : Boolean{
        return firebase.setFavoriteStatus(bookId, isFavorite)
    }

    override suspend fun setReadStatus(bookId: String, isRead: Boolean) : Boolean {
        return firebase.setReadStatus(bookId, isRead)
    }

    override suspend fun saveBook(book: Book): Result<Book> {
        return firebase.saveBook(book)
    }


    override suspend fun getBookById(bookId: String): Book {
        val remote = firebase.getBookById(bookId)
        return remote.toDomain()
    }

    override suspend fun getFavBooks() : List<Book>{
        val local = room.getFavBooks()
        return local.dbListToDomain()
    }

    override suspend fun getReadBooks() : List<Book>{
        val local = room.getReadBooks()
        return local.dbListToDomain()

    }

    override suspend fun getBooksByCategory(category: String) : List<Book>{
        val local = room.getBooksByCategory(category)
        return local.dbListToDomain()
    }




}

