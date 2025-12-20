package com.example.bookstorev2.presentation.navigation

sealed class Screen(val route: String) {
    object BookList : Screen("book_list")
    object Login : Screen("login")
    object AddBook : Screen("add_book")
    object DetailScreen : Screen("book_detail")
}