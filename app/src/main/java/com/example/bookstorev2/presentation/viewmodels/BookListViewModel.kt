package com.example.bookstorev2.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.usecases.GetAllBooksUseCase
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
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val toggleReadUseCase: ToggleReadUseCase
    ) : ViewModel() {



    private val _uiState = MutableStateFlow(BookListUiState())
    val uiState: StateFlow<BookListUiState> = _uiState




    init {
        loadBooks("All")
    }


    fun loadBooks(category: String = "All") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val books = getAllBooksUseCase(category)
                _uiState.value = _uiState.value.copy(
                    books = books,
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.d("Room", "DID NOT LOAD BOOKS")
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load books",
                    isLoading = false
                )
            }
        }
    }

    fun onFavoriteClick(bookId: String) {
        viewModelScope.launch {
            val favStatus = toggleFavoriteUseCase(bookId)
            var updatedBook: Book? = _uiState.value.books.find { it.key == bookId }
            updatedBook = updatedBook?.copy(
                favorite = favStatus
            )
            _uiState.value = _uiState.value.copy(
                books = _uiState.value.books.map { book ->
                    if (book.key == updatedBook?.key) updatedBook else book
                }
            )
        }

        _uiState.value = _uiState.value.copy(

        )
    }

    fun onReadClick(bookId: String) {
        viewModelScope.launch {
            val readStatus = toggleReadUseCase(bookId)
            var updatedBook: Book? = _uiState.value.books.find { it.key == bookId }
            updatedBook = updatedBook?.copy(
                read = readStatus
            )
            _uiState.value = _uiState.value.copy(
                books = _uiState.value.books.map { book ->
                    if (book.key == updatedBook?.key) updatedBook else book
                }
            )

        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
        loadBooks()
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