package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import javax.inject.Inject

class SaveBookUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke(book: Book){
        bookRepo.saveBook(book)
    }
}