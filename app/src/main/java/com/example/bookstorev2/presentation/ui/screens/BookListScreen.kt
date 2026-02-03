package com.example.bookstorev2.presentation.ui.screens


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
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bookstorev2.R
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.presentation.ui.components.BookItem
import com.example.bookstorev2.presentation.ui.components.DrawerBody
import com.example.bookstorev2.presentation.ui.components.DrawerHeader
import com.example.bookstorev2.presentation.viewmodels.viewmodels.BookListViewModel


@Composable
fun BookListScreen(
    bookViewModel: BookListViewModel = hiltViewModel(),
    onLogoutClick: () -> Unit,
    onAdminClick: () -> Unit,
    onNavigateToEditBook: (String) -> Unit,
    onNavigateToDetailScreen: (String) -> Unit,
    navController: NavController,
    user: User,
    onNavigateToSettings: () -> Unit

) {

    val uiState by bookViewModel.uiState.collectAsState()
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
                    }
                }
        }
    }
    LaunchedEffect(user) {
        bookViewModel.loadBooks("All", user.uid)
    }

    LaunchedEffect(Unit) {
        bookViewModel.onAdminCheck()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader()
                DrawerBody(
                    uiState.isAdmin,
                    onAdminClick,
                    onCategoryClick = { item ->
                        bookViewModel.onCategoryChange(item)
                    },
                    onLogoutClick,
                    scope,
                    drawerState,
                    onNavigateToSettings,
                )
            }
        }

    ) {
        Scaffold(

        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .paint(
                        painterResource(R.drawable.book_list_bg),
                        contentScale = ContentScale.FillBounds
                    )
            ) {
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
                                Text(stringResource(R.string.error_retry))
                            }
                        }
                    }

                    else -> {
                        LazyColumn {
                            items(uiState.books) { book ->
                                if (uiState.category == "All") {
                                    BookItem(
                                        uiState.isAdmin,
                                        book = book,
                                        onFavoriteClick = {
                                            bookViewModel.onFavoriteClick(
                                                book,
                                                user.uid
                                            )
                                        },
                                        onReadClick = {
                                            bookViewModel.onReadClick(
                                                book,
                                                user.uid
                                            )
                                        },
                                        onEditClick = { onNavigateToEditBook(book.key) },
                                        onBookClick = { onNavigateToDetailScreen(book.key) }
                                    )
                                } else {
                                    if (book.category == uiState.category) {
                                        BookItem(
                                            uiState.isAdmin,
                                            book = book,
                                            onFavoriteClick = {
                                                bookViewModel.onFavoriteClick(
                                                    book,
                                                    user.uid
                                                )
                                            },
                                            onReadClick = {
                                                bookViewModel.onReadClick(
                                                    book,
                                                    user.uid
                                                )
                                            },
                                            onEditClick = { onNavigateToEditBook(book.key) },
                                            onBookClick = { onNavigateToDetailScreen(book.key) }
                                        )
                                    }
                                }

                            }

                        }
                    }
                }
            }


        }
    }
}






