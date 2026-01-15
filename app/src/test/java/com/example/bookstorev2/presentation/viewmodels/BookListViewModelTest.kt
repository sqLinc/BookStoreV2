package com.example.bookstorev2.presentation.viewmodels

import com.example.bookstorev2.dispatcher.MainDispatcherRule
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.domain.usecases.GetAllBooksUseCase
import com.example.bookstorev2.domain.usecases.ToggleFavoriteUseCase
import com.example.bookstorev2.domain.usecases.ToggleReadUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class BookListViewModelTest {

    private lateinit var bookRepo: BookRepository
    private lateinit var getAllBooksUseCase: GetAllBooksUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private lateinit var toggleReadUseCase: ToggleReadUseCase
    private lateinit var viewModel: BookListViewModel

    @get:Rule
    val mainDispatcher = MainDispatcherRule()

    @Before
    fun setUp(){
        bookRepo = mock()
        getAllBooksUseCase = GetAllBooksUseCase(bookRepo)
        toggleReadUseCase = ToggleReadUseCase(bookRepo)
        toggleFavoriteUseCase = ToggleFavoriteUseCase(bookRepo)
        viewModel = BookListViewModel(
            getAllBooksUseCase,
            toggleFavoriteUseCase,
            toggleReadUseCase
        )

    }


    @Test
    fun load_books_should_update_books_state_if_success() = runTest {
        val expected: List<Book> = listOf(
                Book(
                    "test_key",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    false,
                    false,
                    ""
                    )
                )

        whenever(bookRepo.getAllBooks(any())).thenReturn(expected)


        viewModel.loadBooks("")


        advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals(expected, viewModel.uiState.value.books)


    }


    @Test
    fun load_books_should_update_error_state_if_failed_to_load_books() = runTest {
        whenever(bookRepo.getAllBooks(any())).thenAnswer{throw Exception("Determined error")}

        viewModel.loadBooks("")
        advanceUntilIdle()
        assertEquals("Failed to load books", viewModel.uiState.value.error)
        assertFalse(viewModel.uiState.value.isLoading)
    }


}