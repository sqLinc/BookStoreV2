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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookstorev2.presentation.viewmodels.BookListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookstorev2.presentation.ui.components.BookItem
import com.example.bookstorev2.presentation.ui.components.DrawerBody
import com.example.bookstorev2.presentation.ui.components.DrawerHeader
import com.example.bookstorev2.presentation.ui.state.MainAddScreenNavigation
import com.example.bookstorev2.presentation.ui.state.MainToAddScreenNav
import com.example.bookstorev2.presentation.viewmodels.AddBookViewModel
import com.example.bookstorev2.presentation.viewmodels.LoginViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun BookListScreen(
    bookViewModel: BookListViewModel = hiltViewModel(),
    userViewModel: LoginViewModel = hiltViewModel(),
    addBookViewModel: AddBookViewModel = hiltViewModel(),

    onLoginClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onAdminClick: () -> Unit = {},
    onSuccess: () -> Unit = {},
    onNavigateToEditBook: (String) -> Unit,
    onNavigateToDetailScreen: (String) -> Unit

) {
    val uiState = bookViewModel.uiState.value
    val userUiState = userViewModel.uiState.value


    var uiUserState = userViewModel.uiState.value

        val drawerState = rememberDrawerState(DrawerValue.Open)



    LaunchedEffect(Unit) {
        userViewModel.onAdminCheck()
        bookViewModel.loadBooks()

    }
    LaunchedEffect(key1 = uiState.navigationEvent) {
        when(val event = uiState.navigationEvent){
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
            Column(Modifier.fillMaxWidth(0.7f)){
                DrawerHeader()
                DrawerBody(uiUserState.isAdminState, onAdminClick, onCategoryClick = { item ->
                    bookViewModel.loadBooks(item)



                })


            }
        }

    ) {
        Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onLogoutClick){
                Icon(Icons.Default.Person, "Login")
            }
        }
    ){ padding  ->
        Box(modifier = Modifier.padding(padding) ){
            when{
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Text("Error:${uiState.error}")
                        Button(onClick = {bookViewModel.clearError()}){
                            Text("Retry")
                        }
                    }
                }
                else -> {
                    LazyColumn {
                        items(uiState.books) {book ->
                            BookItem(
                                uiUserState.isAdminState,
                                book = book,
                                onFavoriteClick = {bookViewModel.onFavoriteClick(book.key)},
                                onReadClick = {bookViewModel.onReadClick(book.key)},
                                onEditClick = { bookId ->
                                    onNavigateToEditBook(bookId)

                                },
                                onBookClick = { bookId ->
                                    onNavigateToDetailScreen(bookId)
                                }
                            )
                        }
                    }


                }
            }
        }


    }
    }



}
/*suspend fun isAdmin(): Boolean{
    val currentUid = Firebase.auth.currentUser!!.uid
    return Firebase.firestore.collection("admin").document(currentUid).get().await().getBoolean("isAdmin") ?: false
}*/