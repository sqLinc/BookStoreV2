package com.example.bookstorev2.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.domain.usecases.GetAllBooksUseCase
import com.example.bookstorev2.domain.usecases.ToggleFavoriteUseCase
import com.example.bookstorev2.domain.usecases.ToggleReadUseCase
import com.example.bookstorev2.presentation.ui.state.BookListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val toggleReadUseCase: ToggleReadUseCase
    ) : ViewModel() {



    private val _uiState = mutableStateOf(BookListUiState())
    val uiState: State<BookListUiState> = _uiState







    fun loadBooks(category: String = "") {
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
            toggleFavoriteUseCase(bookId)
            loadBooks()
        }
    }

    fun onReadClick(bookId: String) {
        viewModelScope.launch {
            toggleReadUseCase(bookId)
            loadBooks()
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







}