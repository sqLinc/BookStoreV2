package com.example.bookstorev2.presentation.viewmodels

import com.example.bookstorev2.dispatcher.MainDispatcherRule
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.domain.usecases.GetBookByIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class BookDetailViewModelTest {

    private lateinit var bookRepo: BookRepository
    private lateinit var getBookUseCase: GetBookByIdUseCase
    private lateinit var viewModel: BookDetailViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp(){
        bookRepo = mock()
        getBookUseCase = GetBookByIdUseCase(bookRepo)
        viewModel = BookDetailViewModel(
            getBookUseCase
        )
    }

    @Test
    fun should_update_states_if_got_book() = runTest {

        val key = "test_key"
        val book= Book(
            key = key,
            title = "test_title",
            imageUrl = "test_url",
            category = "test_category",
            description = "test_description",
            price = "test_price",
            date = "test_date",
            author = "test_author",
            favorite = false,
            read = false,
            selectedImage = "null"
        )

        whenever(bookRepo.getBookById(any())).thenReturn(book)

        viewModel.getBook(key)
        advanceUntilIdle()
        assertEquals(key, viewModel.uiState.value.key)

    }

    @Test
    fun should_throw_exception_if_failed_to_get_book() = runTest {
        val expectedKey = "test_key"

        val expectedError = "Book not found with id: $expectedKey"

        whenever(bookRepo.getBookById(expectedKey)).thenAnswer{throw Exception(expectedError)}

        viewModel.getBook(expectedKey)
        advanceUntilIdle()

        assertEquals(expectedError, viewModel.uiState.value.error)


    }


}