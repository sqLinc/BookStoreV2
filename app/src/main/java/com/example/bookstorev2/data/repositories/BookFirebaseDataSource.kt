package com.example.bookstorev2.data.repositories

import com.example.bookstorev2.R
import com.example.bookstorev2.data.local.room.dto.BookDto
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.models.FirebaseCollections.BOOKS
import com.example.bookstorev2.domain.models.FirebaseCollections.FAVORITE
import com.example.bookstorev2.domain.models.FirebaseCollections.READ
import com.example.bookstorev2.domain.models.FirebaseCollections.USERS
import com.example.bookstorev2.exceptions.toAppException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookFirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {

    suspend fun getFavIds(uid: String) : List<String>{
        return db
            .collection(USERS)
            .document(uid)
            .collection(FAVORITE)
            .get()
            .await()
            .documents
            .map { it.id }
    }

    suspend fun getReadIds(uid: String): List<String>{
        return db
            .collection(USERS)
            .document(uid)
            .collection(READ)
            .get()
            .await()
            .documents.map { it.id }
    }

    suspend fun getBooks() : List<BookDto>{
        try {
            return db
                .collection(BOOKS)
                .get()
                .await()
                .toObjects(BookDto::class.java)
        } catch (e: Exception){
            throw e.toAppException()
        }
    }

    suspend fun isFavorite(bookId: String, uid: String) : Boolean {
        try {
            return db
                .collection(USERS)
                .document(uid)
                .collection(FAVORITE)
                .document(bookId)
                .get()
                .await()
                .exists()
        } catch (e: Exception){
            throw e.toAppException()
        }
    }

    suspend fun isRead(bookId: String, uid: String) : Boolean {
        try {
            return db
                .collection(USERS)
                .document(uid)
                .collection(READ)
                .document(bookId)
                .get()
                .await()
                .exists()
        } catch (e: Exception){
            throw e.toAppException()
        }
    }

    suspend fun setFavoriteStatus(bookId: String, isFavorite: Boolean, uid: String) : Boolean{
        try {
            if (isFavorite){
                db
                    .collection(USERS)
                    .document(uid)
                    .collection(FAVORITE)
                    .document(bookId)
                    .set(emptyMap<String, Any>())
                    .await()
                return true
            }
            else{
                db
                    .collection(USERS)
                    .document(uid)
                    .collection(FAVORITE)
                    .document(bookId)
                    .delete()
                return false
            }


        } catch (e: Exception) {
            throw e.toAppException()
        }
    }

    suspend fun setReadStatus(bookId: String, isRead: Boolean, uid: String) : Boolean {
        try {
            if (isRead){
                db
                    .collection(USERS)
                    .document(uid)
                    .collection(READ)
                    .document(bookId)
                    .set(emptyMap<String, Any>())
                    .await()
                return true
            }
            else{
                db
                    .collection(USERS)
                    .document(uid)
                    .collection(READ)
                    .document(bookId)
                    .delete()
                return false
            }


        } catch (e: Exception) {
            throw e.toAppException()
        }
    }

    suspend fun saveBook(book: Book) : Result<Book>{
        return try {
            val key = book.key.ifEmpty {
                db
                .collection(BOOKS)
                .document()
                .id
            }
            val savedBook = book.copy(key = key)
            db
                .collection(BOOKS)
                .document(key)
                .set(savedBook)
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
            throw e.toAppException()
        }
    }

    suspend fun getBookById(bookId: String): BookDto {
        return try {
            val task = db
                .collection(BOOKS)
                .document(bookId)
                .get()
                .await()
            task.toObject(BookDto::class.java) ?: throw Exception("${R.string.error_book_not_found} $bookId")

        } catch(e: Exception) {
            throw e.toAppException()
        }
    }
}