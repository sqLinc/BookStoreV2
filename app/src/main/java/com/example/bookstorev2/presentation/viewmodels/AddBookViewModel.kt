package com.example.bookstorev2.presentation.viewmodels

import android.content.ContentResolver
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.domain.usecases.ChooseImageUseCase
import com.example.bookstorev2.domain.usecases.SaveBookUseCase
import com.example.bookstorev2.presentation.ui.state.AddBookUiState
import com.example.bookstorev2.presentation.ui.state.MainAddScreenNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val bookRepo: BookRepository,
    private val saveBook: SaveBookUseCase,
    private val chooseImage: ChooseImageUseCase,

) : ViewModel() {



    private val _uiState = mutableStateOf(AddBookUiState())
    val uiState: State<AddBookUiState> = _uiState


    fun onTitleChange(title: String){
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onImageUrlChange(imageUrl: String){
        _uiState.value = _uiState.value.copy(imageUrl = imageUrl)
    }

    fun onCategoryChange(category: String){
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun onDescriptionChange(desc: String){
        _uiState.value = _uiState.value.copy(description = desc)
    }

    fun onPriceChange(price: String){
        _uiState.value = _uiState.value.copy(price = price)
    }

    fun onDateChange(date: String){
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun onAuthorChange(author: String){
        _uiState.value = _uiState.value.copy(author = author)
    }
    fun onSaveClick(cv: ContentResolver){
        viewModelScope.launch {
            val result = saveBook(
                book = Book(
                        key = "",
                        title = _uiState.value.title,
                        imageUrl = if(_uiState.value.selectedImageUri != null)
                            imageToBase64(
                                _uiState.value.selectedImageUri!!,
                                cv
                            )
                            else "",
                        category = _uiState.value.category,
                        description = _uiState.value.description,
                        price = _uiState.value.price,
                        date = _uiState.value.date,
                        author = uiState.value.author,

                        )
            )
            result.fold(
                onSuccess = { userData ->
                    _uiState.value = _uiState.value.copy(
                        navigationEvent = MainAddScreenNavigation.NavigateOnSaved(userData)
                    )



                },
                onFailure = { e  ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Error while saving book"
                    )

                }
            )

        }
    }

    fun onChooseImage(imagePickerLauncher: ManagedActivityResultLauncher<String, Uri?>){
        viewModelScope.launch {
            chooseImage(imagePickerLauncher)
        }
    }

    private fun imageToBase64(uri: Uri, contentResolver: ContentResolver) : String{
        val inputStream = contentResolver.openInputStream(uri)

        val bytes = inputStream?.readBytes()
        return bytes?.let{
            Base64.encodeToString(it, Base64.DEFAULT)
        } ?: ""
    }

    fun onNavigationConsumed(){
        _uiState.value = _uiState.value.copy(
            navigationEvent = null
        )
    }








}