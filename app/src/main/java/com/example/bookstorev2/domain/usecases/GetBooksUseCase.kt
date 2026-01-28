package com.example.bookstorev2.domain.usecases


import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke(category: String = "All") : List<Book> {
        return when (category) {
            "Favorite" -> bookRepo.getFavBooks()
            "Read" -> bookRepo.getReadBooks()
            "All" -> bookRepo.getAllBooks()

            else -> bookRepo.getBooksByCategory(category)

        }


    }
}