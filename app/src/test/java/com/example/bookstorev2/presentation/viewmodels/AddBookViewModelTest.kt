package com.example.bookstorev2.presentation.viewmodels

import androidx.compose.ui.platform.LocalContext
import com.example.bookstorev2.data.repositories.ImageRefactor
import com.example.bookstorev2.dispatcher.MainDispatcherRule
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.domain.repositories.ImageRefactorRepository
import com.example.bookstorev2.domain.usecases.GetBookByIdUseCase
import com.example.bookstorev2.domain.usecases.SaveBookUseCase
import com.example.bookstorev2.presentation.navigation.onSavedSuccess
import com.example.bookstorev2.presentation.ui.state.MainAddScreenNavigation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class AddBookViewModelTest {

    private lateinit var bookRepo: BookRepository
    private lateinit var saveBookUseCase: SaveBookUseCase
    private lateinit var getBookUseCase: GetBookByIdUseCase
    private lateinit var imageRef: ImageRefactorRepository
    private lateinit var viewModel: AddBookViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp(){
        bookRepo = mock()
        imageRef = mock()
        saveBookUseCase = SaveBookUseCase(bookRepo)
        getBookUseCase = GetBookByIdUseCase(bookRepo)
        viewModel = AddBookViewModel(
            bookRepo,
            saveBookUseCase,
            getBookUseCase,
            imageRef
        )
    }

    @Test
    fun onTitleChange_updates_title_state() = runTest {
        val title = "test_title"
        viewModel.onTitleChange(title)
        assertEquals(title, viewModel.uiState.value.title)
    }
    @Test
    fun onImageUrlChange_updates_image_url_state() = runTest {
        val imageUrl = "test_url"
        viewModel.onImageUrlChange(imageUrl)
        assertEquals(imageUrl, viewModel.uiState.value.imageUrl)
    }
    @Test
    fun onCategoryChange_updates_category_state() = runTest {
        val category = "test_category"
        viewModel.onCategoryChange(category)
        assertEquals(category, viewModel.uiState.value.category)
    }
    @Test
    fun onDescriptionChange_updates_desc_state() = runTest {
        val desc = "test_desc"
        viewModel.onDescriptionChange(desc)
        assertEquals(desc, viewModel.uiState.value.description)
    }
    @Test
    fun onPriceChange_updates_price_state() = runTest {
        val price = "test_price"
        viewModel.onPriceChange(price)
        assertEquals(price, viewModel.uiState.value.price)
    }
    @Test
    fun onDateChange_updates_date_state() = runTest {
        val date = "test_date"
        viewModel.onDateChange(date)
        assertEquals(date, viewModel.uiState.value.date)
    }
    @Test
    fun onAuthorChange_updates_author_state() = runTest {
        val author = "test_author"
        viewModel.onAuthorChange(author)
        assertEquals(author, viewModel.uiState.value.author)
    }

    @Test
    fun should_update_book_states() = runTest{
        val book= Book(
            key = "test_key",
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

        whenever(bookRepo.getBookById(book.key)).thenReturn(book)

        viewModel.onBookIdUpdate(book.key)
        advanceUntilIdle()

        assertEquals(book.key, viewModel.uiState.value.key)



    }

    @Test
    fun should_update_navigationEvent_state_value_when_success() = runTest {

        val book = Book(
            key = "test_key",
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
        val key = MainAddScreenNavigation.NavigateOnSaved(onSavedSuccess(book.key))

        whenever(bookRepo.saveBook(any())).thenReturn(Result.success(onSavedSuccess(book.key)))



        viewModel.onSaveClick()
        advanceUntilIdle()
        assertEquals(key, viewModel.uiState.value.navigationEvent)
        verify(bookRepo).saveBook(any())

    }

    @Test
    fun should_throw_error_if_failed_to_save() = runTest {
        val expectedError = "Error while saving book"

        whenever(bookRepo.saveBook(any())).thenReturn(Result.failure(Throwable(expectedError)))

        viewModel.onSaveClick()
        advanceUntilIdle()
        assertEquals(expectedError, viewModel.uiState.value.error)

    }






}