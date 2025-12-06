package com.example.bookstorev2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookstorev2.presentation.navigation.Screen
import com.example.bookstorev2.presentation.ui.components.AddBookScreen
import com.example.bookstorev2.presentation.ui.components.DrawerBody
import com.example.bookstorev2.presentation.ui.screens.BookListScreen
import com.example.bookstorev2.presentation.ui.screens.LoginScreen
import com.ramcosta.composedestinations.DestinationsNavHost
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
                        }
                    )
                }
                composable(Screen.AddBook.route){
                    AddBookScreen(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }


            }
        }
    }
}