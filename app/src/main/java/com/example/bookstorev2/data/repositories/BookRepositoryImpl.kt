package com.example.bookstorev2.data.repositories

import com.example.bookstorev2.data.local.room.mapper.bookListToDb
import com.example.bookstorev2.data.local.room.mapper.dbListToDomain
import com.example.bookstorev2.data.local.room.mapper.dtoListToDomain
import com.example.bookstorev2.data.local.room.mapper.toDb
import com.example.bookstorev2.data.local.room.mapper.toDomain
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BookRepositoryImpl @Inject constructor(
    private val room: BookRoomDataSource,
    private val firebase: BookFirebaseDataSource
) : BookRepository {


    override suspend fun getFavIds(uid: String): List<String> {
        return firebase.getFavIds(uid)
    }

    override suspend fun getReadIds(uid: String): List<String> {
        return firebase.getReadIds(uid)
    }

    override suspend fun getAllBooks(): List<Book> {
        try {
            val remote = firebase.getBooks()
            return remote.dtoListToDomain()

        } catch (_: Exception){
            val local = room.getBooks()
            return local.dbListToDomain()
        }
    }

    override suspend fun isFavorite(bookId: String, uid: String): Boolean {
        return firebase.isFavorite(bookId, uid)
    }

    override suspend fun isRead(bookId: String, uid: String): Boolean {
        return firebase.isRead(bookId, uid)
    }

    override suspend fun setFavoriteStatus(bookId: String, isFavorite: Boolean, uid: String) : Boolean{
        return firebase.setFavoriteStatus(bookId, isFavorite, uid)
    }

    override suspend fun setReadStatus(bookId: String, isRead: Boolean, uid: String) : Boolean {
        return firebase.setReadStatus(bookId, isRead, uid)
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

    override suspend fun updateLocalBook(book: Book){
        val local = book.toDb()
        room.updatedBook(local)
    }
    override suspend fun saveAllToLocal(books: List<Book>){
        val toDb = books.bookListToDb()
        room.saveAll(toDb)
    }

    override suspend fun deleteAllFromLocal() {
        room.deleteAllFromLocal()
    }




}

