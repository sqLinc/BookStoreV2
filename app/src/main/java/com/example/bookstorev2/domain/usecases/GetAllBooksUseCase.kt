package com.example.bookstorev2.domain.usecases


import android.util.Log
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBooksUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke(category: String = "All", uid: String) : List<Book> {
        Log.d("Room", "INVOKED USE CASE")
        Log.d("Room", category)
        return when (category) {
            "Favorite" -> bookRepo.getFavBooks()
            "Read" -> bookRepo.getReadBooks()
            "All" -> bookRepo.getAllBooks(uid)

            else -> bookRepo.getBooksByCategory(category)

        }


    }
}