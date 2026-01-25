package com.example.bookstorev2.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.domain.usecases.GetAllBooksUseCase
import com.example.bookstorev2.domain.usecases.ToggleFavoriteUseCase
import com.example.bookstorev2.domain.usecases.ToggleReadUseCase
import com.example.bookstorev2.presentation.ui.state.BookListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val bookRepo: BookRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val toggleReadUseCase: ToggleReadUseCase
) : ViewModel() {



    private val _uiState = MutableStateFlow(BookListUiState())
    val uiState: StateFlow<BookListUiState> = _uiState











    fun loadBooks(category: String = "All", uid: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val books = getAllBooksUseCase(category, uid)
                Log.d("Room", "GOT BOOK FROM REPO")
                val favIds = bookRepo.getFavIds(uid)
                val readIds = bookRepo.getReadIds(uid)
                Log.d("Room", "GOT IDS OF FAV AND READ")
                val favSet = favIds.toSet()
                val readSet = readIds.toSet()
                val updatedBooks: List<Book> = books.map { book ->
                    book.copy(
                        favorite = book.key in favSet,
                        read = book.key in readSet
                    )
                }
                Log.d("Room", "GOT UPDATED LIST")
                _uiState.value = _uiState.value.copy(
                    books = updatedBooks,
                    isLoading = false
                )
                bookRepo.saveAllToLocal(_uiState.value.books)

            } catch (e: Exception) {
                Log.d("Room", "DID NOT LOAD BOOKS")
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


    fun onNavigationConsumed(){
        _uiState.value = _uiState.value.copy(
            navigationEvent = null
        )
    }

    fun updateBookListState(updated: Book){
        Log.d("New/Updated", "Starting to operate")
       if (_uiState.value.books.any { it.key == updated.key }){
           _uiState.value = _uiState.value.copy(
               books = _uiState.value.books.map { book ->
                   if(book.key == updated.key) updated else book
               }
           )
           Log.d("New/Updated", "Book is updated!")
       } else{
           _uiState.value = _uiState.value.copy(
               books = _uiState.value.books + updated
           )
           Log.d("New/Updated", "Book is new!")
       }


    }







}