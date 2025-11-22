package com.example.bookstorev2.domain.usecases


import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBooksUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke() : List<Book> {
        return bookRepo.getAllBooks()
    }
}