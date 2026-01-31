package com.example.bookstorev2.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.domain.repositories.ImageRefactorRepository
import com.example.bookstorev2.domain.usecases.SaveBookUseCase
import com.example.bookstorev2.presentation.ui.state.AddBookUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val bookRepo: BookRepository,
    private val saveBook: SaveBookUseCase,
    private val imageRef: ImageRefactorRepository

) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState: StateFlow<AddBookUiState> = _uiState


    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onImageUrlChange(imageUrl: String) {
        _uiState.value = _uiState.value.copy(base64Image = imageUrl)
    }

    fun onCategoryChange(category: String) {
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun onDescriptionChange(desc: String) {
        _uiState.value = _uiState.value.copy(description = desc)
    }

    fun onPriceChange(price: String) {
        _uiState.value = _uiState.value.copy(price = price)
    }

    fun onDateChange(date: String) {
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun onAuthorChange(author: String) {
        _uiState.value = _uiState.value.copy(author = author)
    }

    fun onSelectedUriChange(selectedImageUri: Uri?) {
        viewModelScope.launch {
            val size = imageRef.getFileSize(selectedImageUri!!)
            val base64Image = imageRef.uriToBase64(selectedImageUri)
            if (size > 1_048_487L) {
                _uiState.value = _uiState.value.copy(
                    error = "Image is too big"
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    imageUri = selectedImageUri,
                    base64Image = base64Image!!,
                    error = ""
                )
            }
        }

    }

    suspend fun onBookIdUpdate(bookId: String) {
        val book = bookRepo.getBookById(bookId)

        _uiState.value = _uiState.value.copy(
            description = book.description
        )

        _uiState.value = _uiState.value.copy(
            title = book.title
        )
        _uiState.value = _uiState.value.copy(
            key = book.key
        )
        _uiState.value = _uiState.value.copy(
            base64Image = book.base64Image!!

        )
        _uiState.value = _uiState.value.copy(
            category = book.category
        )

        _uiState.value = _uiState.value.copy(
            price = book.price
        )
        _uiState.value = _uiState.value.copy(
            date = book.date
        )
        _uiState.value = _uiState.value.copy(
            author = book.author
        )
        _uiState.value = _uiState.value.copy(
            isRead = book.read
        )
        _uiState.value = _uiState.value.copy(
            isFavorite = book.favorite
        )
        _uiState.value = _uiState.value.copy(
            imageUri = book.imageUri.toUri()
        )

    }

    fun onSaveClick() {

        viewModelScope.launch {
            val result = saveBook(
                book = Book(
                    key = _uiState.value.key,
                    title = _uiState.value.title,
                    base64Image = _uiState.value.base64Image,
                    category = _uiState.value.category,
                    description = _uiState.value.description,
                    price = _uiState.value.price,
                    date = _uiState.value.date,
                    author = _uiState.value.author,
                    read = _uiState.value.isRead,
                    favorite = _uiState.value.isFavorite,
                    imageUri = _uiState.value.imageUri.toString()
                )
            )
            result.fold(
                onSuccess = { savedBook ->
                    _uiState.value = _uiState.value.copy(savedBook = savedBook)
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(error = e.message ?: "Save error")
                }
            )

        }
    }

    fun onNavigationConsumed() {
        _uiState.value = _uiState.value.copy(
            savedBook = null
        )
    }

}









