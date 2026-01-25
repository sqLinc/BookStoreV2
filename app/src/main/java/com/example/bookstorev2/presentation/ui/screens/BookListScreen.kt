package com.example.bookstorev2.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

import com.example.bookstorev2.R
import com.example.bookstorev2.data.repositories.SettingsRepository
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.presentation.ui.components.BookItem
import com.example.bookstorev2.presentation.ui.components.DrawerBody
import com.example.bookstorev2.presentation.ui.components.DrawerHeader
import com.example.bookstorev2.presentation.ui.state.MainToAddScreenNav
import com.example.bookstorev2.presentation.viewmodels.AppViewModel
import com.example.bookstorev2.presentation.viewmodels.BookListViewModel
import com.example.bookstorev2.presentation.viewmodels.LoginViewModel
import com.example.bookstorev2.presentation.viewmodels.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest


import kotlinx.coroutines.launch


@Composable
fun BookListScreen(
    bookViewModel: BookListViewModel = hiltViewModel(),
    userViewModel: LoginViewModel = hiltViewModel(),
    appViewModel: AppViewModel = hiltViewModel(),

    onLogoutClick: () -> Unit,
    onAdminClick: () -> Unit,
    onSuccess: () -> Unit,
    onNavigateToEditBook: (String) -> Unit,
    onNavigateToDetailScreen: (String) -> Unit,
    navController: NavController,
    settingsViewModel: SettingsViewModel,
    language: String,
    user: User



) {


//    val appViewModel: AppViewModel = hiltViewModel()
    //val user by appViewModel.user.collectAsState()
    Log.d("appvm", "User: $user.toString()")

    val uiState by bookViewModel.uiState.collectAsState()
    val uiUserState = userViewModel.uiState.value

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val backStackEntry by navController.currentBackStackEntryAsState()

    val scope = rememberCoroutineScope()




    backStackEntry?.let { entry ->
        val savedStateHandle = entry.savedStateHandle
        LaunchedEffect(savedStateHandle) {
            savedStateHandle.getStateFlow<Book?>("new_book", null)
                .collect { book ->
                    book?.let {
                        bookViewModel.updateBookListState(it)
                        savedStateHandle.remove<Book>("new_book")
                        Log.d("Nav", "Book after update: $it")
                    }
                }
        }
    }
    LaunchedEffect(user) {
        bookViewModel.loadBooks("All", user.uid)
    }


    LaunchedEffect(Unit) {
        userViewModel.onAdminCheck()
//        bookViewModel.loadBooks("All")


    }

    LaunchedEffect(key1 = uiState.navigationEvent) {
        when (val event = uiState.navigationEvent) {
            is MainToAddScreenNav.NavigateOnEdit -> {
                onSuccess()
                bookViewModel.onNavigationConsumed()
            }

            null -> Unit

        }

    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader()
                DrawerBody(uiUserState.isAdminState, onAdminClick
                , onCategoryClick = { item ->
                    bookViewModel.loadBooks(item, user.uid)
                }, onLogoutClick, scope, drawerState, settingsViewModel, language)


            }
        }

    ) {
        Scaffold(

        ) { padding ->

            Box(modifier = Modifier.padding(padding).paint(painterResource(R.drawable.book_list_bg), contentScale = ContentScale.FillBounds)) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator()
                    }

                    uiState.error != null -> {

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Error:${uiState.error}")
                            Button(onClick = { bookViewModel.clearError(user.uid) }) {
                                Text("Retry")
                            }
                        }
                    }

                    else -> {
                        LazyColumn {
                            items(uiState.books) { book ->
                                BookItem(
                                    uiUserState.isAdminState,
                                    book = book,
                                    onFavoriteClick = { bookViewModel.onFavoriteClick(book, user.uid ) },
                                    onReadClick = { bookViewModel.onReadClick(book, user.uid) },
                                    onEditClick = {

                                            onNavigateToEditBook(book.key)
                                            Log.d("Nav", "book: $book")

                                        },
                                        onBookClick = {onNavigateToDetailScreen(book.key) }

                                    )
                                }

                            }
                        }


                    }
                }
            }


        }
    }




