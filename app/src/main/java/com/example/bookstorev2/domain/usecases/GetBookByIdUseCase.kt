package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke(bookId: String) : Book{
        return bookRepo.getBookById(bookId)
    }
}