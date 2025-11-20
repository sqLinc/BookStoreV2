package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.repositories.BookRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke(bookId: String) {
        val currentStatus = bookRepo.isFavorite(bookId)
        bookRepo.setFavoriteStatus(bookId, !currentStatus)
    }
}