package com.example.bookstorev2.data.local.room

import android.util.Log
import com.example.bookstorev2.data.local.room.dto.BookDto
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.presentation.navigation.onSavedSuccess
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.text.get
import kotlin.text.set

class BookFirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {

    val path = "books"

    suspend fun getBooks() : List<BookDto>{
        Log.d("Room", "Firebase  getBooks() is working...")
        try {
            Log.d("Room", "Firebase getBooks() is TRYING TO GET BOOKS")
            val task = db.collection("books").get().await()
            Log.d("Room", "Firebase getBooks() SUCCESS TO GET BOOKS")
            val list = task.toObjects(BookDto::class.java)
            Log.d("Room", "Firebase getBooks() is SUCCESS TO OBJECTS")
            return list



        } catch (e: Exception){
            throw e
        }
    }

    suspend fun isFavorite(bookId: String) : Boolean{
        try {
            val task = db.collection(path).document(bookId).get().await()
            val book = task.toObject(Book::class.java)
            return book?.favorite ?: false
        } catch (e: Exception){
            throw e
        }
    }

    suspend fun isRead(bookId: String) : Boolean {
        try {
            val task = db.collection(path).document(bookId).get().await()
            val book = task.toObject(Book::class.java)
            return book?.read ?: false
        } catch (e: Exception){
            throw e
        }
    }

    suspend fun setFavoriteStatus(bookId: String, isFavorite: Boolean){
        try {
            db.collection(path).document(bookId)
                .update("favorite", isFavorite)
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun setReadStatus(bookId: String, isRead: Boolean) {
        try{
            db.collection(path).document(bookId)
                .update("read", isRead).await()
        } catch (e: Exception){
            throw e
        }
    }

    suspend fun saveBook(book: Book) : Result<onSavedSuccess>{
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

    suspend fun getBookById(bookId: String): BookDto {
        return try {
            val task = db.collection("books").document(bookId).get().await()
            task.toObject(BookDto::class.java) ?: throw Exception("Book not found with id: $bookId")

        } catch(e: Exception) {
            throw e
        }
    }

    suspend fun getFavBooks() : List<BookDto>{
        try {
            val task = db.collection(path).whereEqualTo("favorite", true).get().await()
            val list = task.toObjects(BookDto::class.java)
            return list
        } catch (e: Exception){
            throw e
        }
    }

    suspend fun getReadBooks() : List<BookDto>{
        try {
            val task = db.collection(path).whereEqualTo("read", true).get().await()
            val list = task.toObjects(BookDto::class.java)
            return list
        } catch (e:Exception){
            throw e
        }

    }

    suspend fun getBooksByCategory(category: String) : List<BookDto>{
        try {
            val task = when (category) {
                "Fantasy" -> db.collection(path).whereEqualTo("category", "Fantasy").get().await()
                "Detective" -> db.collection(path).whereEqualTo("category", "Detective").get().await()
                "Thriller" -> db.collection(path).whereEqualTo("category", "Thriller").get().await()
                "Drama" -> db.collection(path).whereEqualTo("category", "Drama").get().await()
                "Biopic" -> db.collection(path).whereEqualTo("category", "Biopic").get().await()
                "Adventures" -> db.collection(path).whereEqualTo("category", "Adventures").get().await()

                else -> null
            }
            val list = task!!.toObjects(BookDto::class.java)
            return list

        } catch (e: Exception){
            throw e
        }
    }

}