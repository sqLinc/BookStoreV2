package com.example.bookstorev2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookstorev2.presentation.navigation.Screen
import com.example.bookstorev2.presentation.ui.screens.AddBookScreen
import com.example.bookstorev2.presentation.ui.screens.BookDetailScreen
import com.example.bookstorev2.presentation.ui.screens.BookListScreen
import com.example.bookstorev2.presentation.ui.screens.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Screen.Login.route
            ) {
                composable(Screen.Login.route) {
                    LoginScreen(
                        onNavigateToBookList = {
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
                        },
                        onAdminClick = {
                            navController.navigate(Screen.AddBook.route)
                        },
                        onSuccess = {
                            navController.navigate(Screen.AddBook.route)
                        },
                        onNavigateToEditBook = { bookId ->
                            navController.navigate("${Screen.AddBook.route}/$bookId")

                        },
                        onNavigateToDetailScreen = { bookId ->
                            navController.navigate("${Screen.DetailScreen.route}/$bookId")
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
                ){ backStackEntry ->
                    val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                    AddBookScreen(
                        bookId = bookId,
//                        onBackClick = {
//                            navController.popBackStack()
//                        },
                        onSuccess = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(Screen.AddBook.route) {
                    AddBookScreen(
                        bookId = "",
//                        onBackClick = {
//                            navController.popBackStack()
//                        },
                        onSuccess = {
                            navController.popBackStack()
                        }
                    )
                }






            }
        }
    }
}