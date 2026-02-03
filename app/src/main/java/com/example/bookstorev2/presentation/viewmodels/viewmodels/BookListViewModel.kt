package com.example.bookstorev2.presentation.viewmodels.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.domain.usecases.GetBooksUseCase
import com.example.bookstorev2.domain.usecases.ToggleFavoriteUseCase
import com.example.bookstorev2.domain.usecases.ToggleReadUseCase
import com.example.bookstorev2.presentation.ui.state.BookListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val bookRepo: BookRepository,
    private val userRepo: UserRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val toggleReadUseCase: ToggleReadUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(BookListUiState())
    val uiState: StateFlow<BookListUiState> = _uiState


    suspend fun onAdminCheck() {
        val isAdmin = userRepo.isAdmin()
        if (isAdmin) {
            _uiState.value = _uiState.value.copy(
                isAdmin = true
            )
        }

    }

    fun loadBooks(category: String = "All", uid: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                Log.d("MyLog", "scope is launched")
                val books = getBooksUseCase(category)
                Log.d("MyLog", "got books")
                val favIds = bookRepo.getFavIds(uid)
                val readIds = bookRepo.getReadIds(uid)
                Log.d("MyLog", "got ids")
                val favSet = favIds.toSet()
                val readSet = readIds.toSet()
                val updatedBooks: List<Book> = books.map { book ->
                    book.copy(
                        favorite = book.key in favSet,
                        read = book.key in readSet
                    )
                }
                Log.d("MyLog", "got updated books: $updatedBooks")

                _uiState.value = _uiState.value.copy(
                    books = updatedBooks,
                    isLoading = false
                )
                bookRepo.saveAllToLocal(_uiState.value.books)
                Log.d("MyLog", "Saved to local")

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load books",
                    isLoading = false
                )
            }
        }
    }

    fun onFavoriteClick(book: Book, uid: String) {
        viewModelScope.launch {
            val favStatus = toggleFavoriteUseCase(book.key, uid)


            var updatedBook: Book? = _uiState.value.books.find { it.key == book.key }
            updatedBook = updatedBook?.copy(
                favorite = favStatus
            )
            _uiState.value = _uiState.value.copy(
                books = _uiState.value.books.map { book ->
                    if (book.key == updatedBook?.key) updatedBook else book
                }
            )

            bookRepo.updateLocalBook(updatedBook!!)

        }

    }

    fun onReadClick(book: Book, uid: String) {
        viewModelScope.launch {
            val readStatus = toggleReadUseCase(book.key, uid)


            var updatedBook: Book? = _uiState.value.books.find { it.key == book.key }
            updatedBook = updatedBook?.copy(
                read = readStatus
            )
            _uiState.value = _uiState.value.copy(
                books = _uiState.value.books.map { book ->
                    if (book.key == updatedBook?.key) updatedBook else book
                }
            )

            bookRepo.updateLocalBook(updatedBook!!)

        }
    }

    fun clearError(uid: String) {
        _uiState.value = _uiState.value.copy(error = null)
        loadBooks("All", uid)
    }


    fun updateBookListState(updated: Book) {
        if (_uiState.value.books.any { it.key == updated.key }) {
            _uiState.value = _uiState.value.copy(
                books = _uiState.value.books.map { book ->
                    if (book.key == updated.key) updated else book
                }
            )
        } else {
            _uiState.value = _uiState.value.copy(
                books = _uiState.value.books + updated
            )
        }


    }

    fun onCategoryChange(category: String) {
        _uiState.value = _uiState.value.copy(
            category = category
        )
    }


}