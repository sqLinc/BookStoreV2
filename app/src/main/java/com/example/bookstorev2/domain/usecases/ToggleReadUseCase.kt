package com.example.bookstorev2.domain.usecases

import android.util.Log
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import javax.inject.Inject

class ToggleReadUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke(book: Book, uid: String) : Boolean{
        val currentReadStatus = bookRepo.isRead(book.key, uid)
        Log.d("readLog", "currentStatus is received: $currentReadStatus")
        return bookRepo.setReadStatus(book, !currentReadStatus!!, uid)
    }
}