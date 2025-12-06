package com.example.bookstorev2.data.repositories

import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BookRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : BookRepository {

    val path = "books"

    override suspend fun getAllBooks(): List<Book> {
        try {
            val task = db.collection(path).get().await()
            val list = task.toObjects(Book::class.java)
            return list
        } catch (e: Exception){
            throw e
        }

    }

    override suspend fun isFavorite(bookId: String): Boolean {
        try {
            val task = db.collection(path).document(bookId).get().await()
            val book = task.toObject(Book::class.java)
            return book?.isFavorite ?: false
        } catch (e: Exception){
            throw e
        }

    }

    override suspend fun isRead(bookId: String): Boolean {
        try {
            val task = db.collection(path).document(bookId).get().await()
            val book = task.toObject(Book::class.java)
            return book?.isRead ?: false
        } catch (e: Exception){
            throw e
        }

    }

    override suspend fun setFavoriteStatus(bookId: String, isFavorite: Boolean) {
        try {
            db.collection(path).document(bookId)
                .update("isFavorite", isFavorite)
                .await()
        } catch (e: Exception) {
            throw e
        }

    }

    override suspend fun setReadStatus(bookId: String, isRead: Boolean) {
        try{
            db.collection(path).document(bookId)
                .update("isRead", isRead).await()
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun saveBook(book: Book) {
        try {
            val key = book.key.ifEmpty { db.collection(path).document().id }
            db.collection(path).document(key)
                .set(
                    book.copy(key = key)
                )
                .await()
        } catch (e: Exception){
            throw e
        }

    }



}

