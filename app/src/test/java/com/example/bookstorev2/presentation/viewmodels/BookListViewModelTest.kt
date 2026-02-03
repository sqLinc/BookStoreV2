package com.example.bookstorev2.presentation.viewmodels

import com.example.bookstorev2.dispatcher.MainDispatcherRule
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.domain.usecases.GetBooksUseCase
import com.example.bookstorev2.domain.usecases.ToggleFavoriteUseCase
import com.example.bookstorev2.domain.usecases.ToggleReadUseCase
import com.example.bookstorev2.presentation.viewmodels.viewmodels.BookListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class BookListViewModelTest {

    @Mock private lateinit var bookRepo: BookRepository
    @Mock private lateinit var userRepo: UserRepository
    private lateinit var getBookUseCase: GetBooksUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private lateinit var toggleReadUseCase: ToggleReadUseCase
    private lateinit var viewModel: BookListViewModel

    // Используем UnconfinedTestDispatcher для немедленного выполнения
    @get:Rule
    val mainDispatcher = MainDispatcherRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)



        // Создаем use cases
        getBookUseCase = GetBooksUseCase(bookRepo)
        toggleReadUseCase = ToggleReadUseCase(bookRepo)
        toggleFavoriteUseCase = ToggleFavoriteUseCase(bookRepo)

        viewModel = BookListViewModel(
            getBookUseCase,
            bookRepo,
            userRepo,
            toggleFavoriteUseCase,
            toggleReadUseCase,
        )
    }

    @Test
    fun load_books_should_update_books_state_if_success() = runTest {
        val expected: List<Book> = listOf(
            Book("test_key", "", "", "", "", "", "", "", false, false, "")
        )

        // Настройка моков
        whenever(bookRepo.getAllBooks()).thenReturn(expected)
        whenever(bookRepo.getFavIds("test")).thenReturn(emptyList())
        whenever(bookRepo.getReadIds("test")).thenReturn(emptyList())
        whenever(bookRepo.saveAllToLocal(any())).thenAnswer { Unit }

        // Запускаем метод
        viewModel.loadBooks("All", "test")

        // В UnconfinedTestDispatcher выполнение происходит немедленно
        // Не нужно вызывать advanceUntilIdle()

        // Проверяем результат
        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals(expected, viewModel.uiState.value.books)

        // Проверяем вызовы
        verify(bookRepo, times(1)).getAllBooks()
    }
}