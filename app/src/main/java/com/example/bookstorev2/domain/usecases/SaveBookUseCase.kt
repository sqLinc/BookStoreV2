package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.presentation.navigation.onSavedSuccess
import javax.inject.Inject

class SaveBookUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke(book: Book) : Result<onSavedSuccess>{
        return bookRepo.saveBook(book)
    }
}