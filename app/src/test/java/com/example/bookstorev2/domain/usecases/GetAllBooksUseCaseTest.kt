package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.domain.repositories.BookRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever


const val CATEGORY_READ = "Read"
const val CATEGORY_FAVORITE = "Favorite"
const val CATEGORY_ALL = "All"
const val CATEGORY = "Drama"

class GetAllBooksUseCaseTest {

    private lateinit var bookRepo: BookRepository
    private lateinit var useCase: GetBooksUseCase

    @Before
    fun setUp(){
        bookRepo = mock()
        useCase = GetBooksUseCase(bookRepo)
    }

    @Test
    fun `should return favorite books when category is favorite`() = runTest{

        val category = CATEGORY_FAVORITE
        val expected: List<Book> = listOf(
            Book(
                "test_key",
                "test_title",
                "test_imageUrl",
                "test_category",
                "test_desc",
                "test_price",
                "test_date",
                "test_author",
                favorite = true,
                false,
                "test_selectedImage"
            )

        )

        whenever(bookRepo.getFavBooks()).thenReturn(expected)

        val actual = useCase(category = category)

        assertEquals(expected, actual)
        verify(bookRepo).getFavBooks()
        verifyNoMoreInteractions(bookRepo)

    }

    @Test
    fun `should return read books when category is read`() = runTest{

        val category = CATEGORY_READ
        val expected: List<Book> = listOf(
            Book(
                "test_key",
                "test_title",
                "test_imageUrl",
                "test_category",
                "test_desc",
                "test_price",
                "test_date",
                "test_author",
                false,
                read = true,
                "test_selectedImage"
            )

        )

        whenever(bookRepo.getReadBooks()).thenReturn(expected)

        val actual = useCase(category = category)

        assertEquals(expected, actual)
        verify(bookRepo).getReadBooks()
        verifyNoMoreInteractions(bookRepo)

    }

    @Test
    fun `should return all books when category is empty`() = runTest{

        val category = CATEGORY_ALL
        val expected: List<Book> = listOf(
            Book(
                "test_key",
                "test_title",
                "test_imageUrl",
                "test_category",
                "test_desc",
                "test_price",
                "test_date",
                "test_author",
                false,
                false,
                "test_selectedImage"
            )

        )

        whenever(bookRepo.getAllBooks()).thenReturn(expected)

        val actual = useCase(category = category)

        assertEquals(expected, actual)
        verify(bookRepo).getAllBooks()
        verifyNoMoreInteractions(bookRepo)

    }

    @Test
    fun `should return matched category books when category is specified`() = runTest{

        val category = CATEGORY
        val expected: List<Book> = listOf(
            Book(
                "test_key",
                "test_title",
                "test_imageUrl",
                category = "Drama",
                "test_desc",
                "test_price",
                "test_date",
                "test_author",
                false,
                false,
                "test_selectedImage"
            )

        )

       whenever(bookRepo.getBooksByCategory(category)).thenReturn(expected)

        val actual = useCase(category = category)

        assertEquals(expected, actual)
        verify(bookRepo).getBooksByCategory(category)
        verifyNoMoreInteractions(bookRepo)

    }





}