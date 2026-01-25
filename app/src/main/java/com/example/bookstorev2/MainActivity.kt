package com.example.bookstorev2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.datastore.dataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookstorev2.data.repositories.SettingsRepository
import com.example.bookstorev2.data.repositories.dataStore
import com.example.bookstorev2.domain.models.AppTheme
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.presentation.navigation.Screen
import com.example.bookstorev2.presentation.ui.screens.AddBookScreen
import com.example.bookstorev2.presentation.ui.screens.BookDetailScreen
import com.example.bookstorev2.presentation.ui.screens.BookListScreen
import com.example.bookstorev2.presentation.ui.screens.LoginScreen
import com.example.bookstorev2.presentation.viewmodels.AppViewModel
import com.example.bookstorev2.presentation.viewmodels.SettingsViewModel
import com.example.bookstorev2.ui.theme.setLocale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val appViewModel: AppViewModel = hiltViewModel()
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()
            val language by settingsViewModel.language.collectAsState()
            val uid by settingsViewModel.uid.collectAsState()
            val email by settingsViewModel.email.collectAsState()

            val user = User(uid, email)


            var lastLanguage by remember { mutableStateOf(language) }

            LaunchedEffect(language) {
                if (lastLanguage != language) {
                    setLocale(this@MainActivity, language)
                    recreate()
                    lastLanguage = language
                }

            }






            val startRoute = when{
                uid.isEmpty() -> Screen.Login.route
                else -> Screen.BookList.route
            }





                MaterialTheme(
                    colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
                ) {

                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = startRoute
//                                startDestination = Screen.Login.route

                            ) {
                                composable(Screen.Login.route) {
                                    LoginScreen(
                                        onSuccess = { user ->
                                            navController.navigate(Screen.BookList.route) {
                                                popUpTo(Screen.Login.route) { inclusive = true }
                                            }
                                            Log.d("appvm", "Data is received at MainActivity: $user")
                                            appViewModel.setUser(user)
                                            Log.d("appvm", "Now checking updated appVM: ${appViewModel.user}")



                                        }
                                    )
                                }
                                composable(Screen.BookList.route) {
                                    BookListScreen(
                                        onLogoutClick = {
                                            navController.navigate(Screen.Login.route)
                                        },
                                        onAdminClick = {
                                            navController.navigate(Screen.AddBook.route)
                                        },
                                        onSuccess = {
                                            navController.navigate(Screen.AddBook.route)
                                        },
                                        onNavigateToEditBook = { bookId ->
                                            if (bookId.isEmpty() || bookId.isBlank()){
                                                Log.d("Nav", "Id is empty")

                                            }
                                            else{
                                                navController.navigate("${Screen.AddBook.route}/$bookId")
                                            }


                                        },
                                        onNavigateToDetailScreen = { bookId ->
                                            navController.navigate("${Screen.DetailScreen.route}/$bookId")
                                        },
                                        navController = navController,
                                        appViewModel = appViewModel,
                                        settingsViewModel = settingsViewModel,
                                        language = language,
                                        user = user

                                        )
                                }



                                composable(
                                    route = "${Screen.DetailScreen.route}/{bookId}",
                                    arguments = listOf(
                                        navArgument("bookId") {
                                            type = NavType.StringType
                                        }
                                    )
                                ) { backStackEntry ->
                                    val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                                    BookDetailScreen(
                                        onBackClick = {
                                            navController.navigate(Screen.BookList.route)
                                        },
                                        bookId = bookId
                                    )

                                }
                                composable(
                                    route = "${Screen.AddBook.route}/{bookId}",
                                    arguments = listOf(
                                        navArgument("bookId") {
                                            type = NavType.StringType
                                        }
                                    )
                                ){ backStackEntry ->
                                    val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                                    AddBookScreen(
                                        bookId = bookId,
//                        onBackClick = {
//                            navController.popBackStack()
//                        },
                                        onSuccess = { book ->
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_book", book)
                                            navController.popBackStack()
                                        },
                                        navController = navController,
                                        onBackClick = {
                                            navController.popBackStack()
                                        },
                                        appViewModel = appViewModel

                                    )
                                    Log.d("Nav", "IN NAVIGATION: trying to edit: $bookId")
                                }

                                composable(Screen.AddBook.route) {
                                    AddBookScreen(
                                        bookId = "",
//                        onBackClick = {
//                            navController.popBackStack()
//                        },
                                        onSuccess = { book ->
                                            navController.previousBackStackEntry
                                                ?.savedStateHandle
                                                ?.set("new_book", book)
                                            navController.popBackStack()
                                            Log.d("Nav", "IN NAVIGATION: trying to create: key:${book?.key} ")
                                        },
                                        navController = navController,
                                        onBackClick = {
                                            navController.popBackStack()
                                        },
                                        appViewModel = appViewModel
                                    )

                                }










                            }
                        }
                    }

                }
            }


        }
