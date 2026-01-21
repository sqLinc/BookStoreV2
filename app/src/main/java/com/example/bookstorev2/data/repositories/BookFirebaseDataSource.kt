package com.example.bookstorev2.data.repositories

import android.util.Log
import com.example.bookstorev2.data.local.room.dto.BookDto
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.presentation.navigation.onSavedSuccess
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookFirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {

    val path = "books"

    suspend fun getBooks() : List<BookDto>{
        try {
            val task = db.collection("books").get().await()
            val list = task.toObjects(BookDto::class.java)
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

    suspend fun setFavoriteStatus(bookId: String, isFavorite: Boolean) : Boolean{
        try {
            db.collection(path).document(bookId)
                .update("favorite", isFavorite)
                .await()
            return isFavorite
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun setReadStatus(bookId: String, isRead: Boolean) : Boolean {
        try{
            db.collection(path).document(bookId)
                .update("read", isRead).await()
            return isRead
        } catch (e: Exception){
            throw e
        }
    }

    suspend fun saveBook(book: Book) : Result<Book>{
        return try {
            val key = book.key.ifEmpty { db.collection(path).document().id }
            val savedBook = book.copy(key = key)
            Log.d("Nav", "Creating key: $key")
            db.collection(path).document(key)
                .set(
                    savedBook
                )
                .await()

            Result.success(
                Book(
                    key = savedBook.key,
                    title = savedBook.title,
                    imageUrl = savedBook.imageUrl,
                    category = savedBook.category,
                    description = savedBook.description,
                    price = savedBook.price,
                    date = savedBook.date,
                    author = savedBook.author,
                    favorite = savedBook.favorite,
                    read = savedBook.read,
                    selectedImage = savedBook.selectedImage
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
            val task = db.collection(path).whereEqualTo("category", category).get().await()

            val list = task!!.toObjects(BookDto::class.java)
            return list

        } catch (e: Exception){
            throw e
        }
    }

}