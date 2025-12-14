package com.example.bookstorev2.data.repositories

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.presentation.navigation.onSavedSuccess
import com.google.firebase.firestore.FirebaseFirestore
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
            return book?.favorite ?: false
        } catch (e: Exception){
            throw e
        }

    }

    override suspend fun isRead(bookId: String): Boolean {
        try {
            val task = db.collection(path).document(bookId).get().await()
            val book = task.toObject(Book::class.java)
            return book?.read ?: false
        } catch (e: Exception){
            throw e
        }

    }

    override suspend fun setFavoriteStatus(bookId: String, isFavorite: Boolean) {
        try {
            db.collection(path).document(bookId)
                .update("favorite", isFavorite)
                .await()
        } catch (e: Exception) {
            throw e
        }

    }

    override suspend fun setReadStatus(bookId: String, isRead: Boolean) {
        try{
            db.collection(path).document(bookId)
                .update("read", isRead).await()
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun saveBook(book: Book) : Result<onSavedSuccess>{
        return try {
            val key = book.key.ifEmpty { db.collection(path).document().id }
            db.collection(path).document(key)
                .set(
                    book.copy(key = key)
                )
                .await()
            Result.success(
                onSavedSuccess(
                    book.key
                )
            )

        } catch (e: Exception){
            throw e

        }

    }

    override suspend fun chooseImage(imagePickerLauncher: ManagedActivityResultLauncher<String, Uri?>) {
        imagePickerLauncher.launch("image/*")
    }

    override suspend fun getBookById(bookId: String): Book {
        return try {
            val task = db.collection("books").document(bookId).get().await()
            task.toObject(Book::class.java) ?: throw Exception("Book not found with id: $bookId")

        } catch(e: Exception) {
            throw e
        }
    }



}

