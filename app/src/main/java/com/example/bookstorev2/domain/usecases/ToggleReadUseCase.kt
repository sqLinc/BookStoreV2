package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.repositories.BookRepository
import javax.inject.Inject

class ToggleReadUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke(bookId: String, uid: String) : Boolean{
        val currentReadStatus = bookRepo.isRead(bookId, uid)
        return bookRepo.setReadStatus(bookId, !currentReadStatus!!, uid)
    }
}