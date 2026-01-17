package com.example.bookstorev2.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.domain.usecases.GetBookByIdUseCase
import com.example.bookstorev2.presentation.ui.state.DetailScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getBookUseCase: GetBookByIdUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(DetailScreenState())
    val uiState: State<DetailScreenState> = _uiState

    fun getBook(bookId: String){
        viewModelScope.launch {
            try {
                val book = getBookUseCase(bookId)
                _uiState.value = _uiState.value.copy(
                    key = book.key,
                    title = book.title,
                    imageUrl = book.imageUrl,
                    category = book.category,
                    description = book.description,
                    price = book.price,
                    date = book.date,
                    author = book.author,
                    favorite = book.favorite,
                    read = book.read
                )

            }
            catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error"
                )
            }
        }

    }







}