package com.example.bookstorev2.domain.usecases

import android.util.Log
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke(book: Book, uid: String) : Boolean {
        val currentStatus = bookRepo.isFavorite(book.key, uid)
        Log.d("fav", "CURRENTStatus is: $currentStatus")
        return bookRepo.setFavoriteStatus(book, !currentStatus!!, uid)
    }
}