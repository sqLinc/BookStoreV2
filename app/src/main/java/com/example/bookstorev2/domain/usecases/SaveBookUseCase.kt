package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import javax.inject.Inject

class SaveBookUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke(book: Book) : Result<Book>{
        if (book.title.isBlank() || !book.title.any { it.isUpperCase() }){
            return Result.failure(IllegalArgumentException("Title must not be blank and must start with upper case"))
        }
        if (book.category.isBlank()){
            return Result.failure(IllegalArgumentException("Category must not be blank!"))
        }
        if (book.price.isBlank() || book.price.any { it.isLetter()} || !book.price.any { it.isDigit() }){
            return Result.failure(IllegalArgumentException("Price must be proper formation"))
        }
        if (book.description.isBlank() || !book.description.any { it.isUpperCase() }){
            return Result.failure(IllegalArgumentException("Description must not be blank!"))
        }
        if (book.author.isBlank()){
            return Result.failure(IllegalArgumentException("Author must be only text and start with upper case!"))
        }
        if (book.date.isBlank() || book.date.any { it.isLetter() }){
            return Result.failure(IllegalArgumentException("Date must not be blank and must contain only digits!!"))
        }
        if (book.imageUrl.isEmpty()){
            return Result.failure(IllegalArgumentException("You must chose image!"))
        }
        else{
            return bookRepo.saveBook(book)
        }

    }
}