package com.example.bookstorev2.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookstorev2.presentation.viewmodels.BookListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.bookstorev2.presentation.ui.components.BookItem


@Composable
fun BookListScreen(

    viewModel: BookListViewModel = hiltViewModel(),
    onLoginClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val uiState = viewModel.uiState.value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onLoginClick){
                Icon(Icons.Default.Person, "Login")
            }
        }
    ){ padding  ->
        Box(modifier = Modifier.padding(padding)){
            when{
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.error != null -> {
                    Column{
                        Text("Error:${uiState.error}")
                        Button(onClick = {viewModel.clearError()}){
                            Text("Retry")
                        }
                    }
                }
                else -> {
                    LazyColumn {
                        items(uiState.books) {book ->
                            BookItem(
                                book = book,
                                onFavoriteClick = {viewModel.onFavoriteClick(book.key)},
                                onReadClick = {viewModel.onReadClick(book.key)}
                            )
                        }
                    }
                }
            }
        }


    }


}