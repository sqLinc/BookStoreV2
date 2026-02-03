package com.example.bookstorev2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.presentation.navigation.Screen
import com.example.bookstorev2.presentation.ui.screens.AddBookScreen
import com.example.bookstorev2.presentation.ui.screens.BookDetailScreen
import com.example.bookstorev2.presentation.ui.screens.BookListScreen
import com.example.bookstorev2.presentation.ui.screens.LoginScreen
import com.example.bookstorev2.presentation.ui.screens.SettingsScreen
import com.example.bookstorev2.presentation.viewmodels.viewmodels.LoginViewModel
import com.example.bookstorev2.presentation.viewmodels.viewmodels.SettingsViewModel
import com.example.bookstorev2.ui.theme.setLocale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val settingsViewModel: SettingsViewModel = hiltViewModel()

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
                }
            }
            val startRoute = when {
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

                    ) {
                        composable(Screen.Login.route) {
                            LoginScreen(
                                viewModel = hiltViewModel<LoginViewModel>(),
                                onSuccess = {
                                    navController.navigate(Screen.BookList.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.BookList.route) {
                            BookListScreen(
                                onLogoutClick = {
                                    navController.navigate(Screen.Login.route)
                                    settingsViewModel.deleteUser()
                                },
                                onAdminClick = {
                                    navController.navigate(Screen.AddBook.route)
                                },
                                onNavigateToEditBook = { bookId ->
                                    navController.navigate("${Screen.AddBook.route}/$bookId")
                                },
                                onNavigateToDetailScreen = { bookId ->
                                    navController.navigate("${Screen.DetailScreen.route}/$bookId")
                                },
                                navController = navController,
                                user = user,
                                onNavigateToSettings = {
                                    navController.navigate(Screen.SettingsScreen.route)
                                }

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
                        ) { backStackEntry ->
                            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                            AddBookScreen(
                                bookId = bookId,
                                onSuccess = { book ->
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("new_book", book)
                                    navController.popBackStack()
                                },
                                onBackClick = {
                                    navController.popBackStack()
                                },
                            )
                        }

                        composable(Screen.AddBook.route) {
                            AddBookScreen(
                                bookId = "",
                                onSuccess = { book ->
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("new_book", book)
                                    navController.popBackStack()
                                },
                                onBackClick = {
                                    navController.popBackStack()
                                },
                            )

                        }
                        composable(Screen.SettingsScreen.route) {
                            SettingsScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                language = language,
                                settingsViewModel = settingsViewModel,
                                onDeletingSuccess = {
                                    navController.navigate(Screen.Login.route)
                                    settingsViewModel.deleteUser()
                                }
                            )
                        }


                    }
                }
            }

        }
    }


}
