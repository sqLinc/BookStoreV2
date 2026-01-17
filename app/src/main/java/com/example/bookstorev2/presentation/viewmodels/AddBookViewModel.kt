package com.example.bookstorev2.presentation.viewmodels

import android.content.ContentResolver
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.domain.repositories.ImageRefactorRepository
import com.example.bookstorev2.domain.usecases.GetBookByIdUseCase
import com.example.bookstorev2.domain.usecases.SaveBookUseCase
import com.example.bookstorev2.presentation.ui.state.AddBookUiState
import com.example.bookstorev2.presentation.ui.state.BookListUiState
import com.example.bookstorev2.presentation.ui.state.MainAddScreenNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val bookRepo: BookRepository,
    private val saveBook: SaveBookUseCase,
    private val getBook: GetBookByIdUseCase,
    private val imageRef: ImageRefactorRepository

) : ViewModel() {

    private val _book = mutableStateOf(BookListUiState())
    val book: State<BookListUiState> = _book

    private val _uiState = mutableStateOf(AddBookUiState())
    val uiState: State<AddBookUiState> = _uiState




    suspend fun onEditBook(bookId: String){
        if (bookId.isNotEmpty()){
            getBook(bookId)
        }
    }





    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onImageUrlChange(imageUrl: String) {
        _uiState.value = _uiState.value.copy(imageUrl = imageUrl)
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
        _uiState.value = _uiState.value.copy(selectedImageUri = selectedImageUri)
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
            imageUrl = book.imageUrl

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
            selectedImageUri = when {
                book.selectedImage != "null" -> book.selectedImage.toUri()
                else -> null
            }
        )
        _uiState.value = _uiState.value.copy(
            isEditing = true
        )

    }

    fun onSaveClick() {
        viewModelScope.launch {
            val result = saveBook(
                book = Book(
                    key = _uiState.value.key,
                    title = _uiState.value.title,

                    imageUrl = when {
                    _uiState.value.isEditing == false -> {
                        if (_uiState.value.selectedImageUri != null) {



                            imageRef.uriToBase64(_uiState.value.selectedImageUri!!)

                        } else {

                            ""
                        }
                    }

                    else -> {
                        if (_uiState.value.imageUrl.isNotEmpty()) {

                            imageRef.uriToBase64(_uiState.value.selectedImageUri!!)
                        } else {
                            if (_uiState.value.selectedImageUri.toString() == "null") {

                                ""
                            } else {

                                imageRef.uriToBase64(_uiState.value.selectedImageUri!!)
                            }
                        }
                    }
                }
                ,
                    category = _uiState.value.category,
                    description = _uiState.value.description,
                    price = _uiState.value.price,
                    date = _uiState.value.date,
                    author = _uiState.value.author,
                    read = _uiState.value.isRead,
                    favorite = _uiState.value.isFavorite,
                    selectedImage = when {
                        _uiState.value.selectedImageUri != null -> _uiState.value.selectedImageUri!!
                        else -> null


                    }.toString()


                )
            )
            result.fold(
                onSuccess = { userData ->
                    _uiState.value = _uiState.value.copy(
                        navigationEvent = MainAddScreenNavigation.NavigateOnSaved(userData)
                    )
                    _uiState.value = _uiState.value.copy(
                        isEditing = false
                    )




                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Error while saving book"

                    )

                }
            )

        }
    }

    fun onChooseImage(imagePickerLauncher: ManagedActivityResultLauncher<String, Uri?>) {
        viewModelScope.launch {
            imagePickerLauncher.launch("image/*")
        }
    }







    fun onNavigationConsumed() {
        _uiState.value = _uiState.value.copy(
            navigationEvent = null
        )
    }




}









