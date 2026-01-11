package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.presentation.navigation.ToMainScreenDataObject
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


class AuthByEmailPassUseCaseTest {

    private lateinit var userRepo: UserRepository
    private lateinit var useCase: AuthByEmailPassUseCase

    @Before
    fun setUp() {
        userRepo = mock()
        useCase = AuthByEmailPassUseCase(userRepo)
    }

    @Test
    fun should_return_the_same_valid_email_and_uid_as_in_repository() = runTest{

        val email = "test@gmail.com"
        val password = "test_password"
        val expected = Result.success(ToMainScreenDataObject("test_uid", email))


        Mockito.`when`(userRepo.loginUserByEmailPass(email, password)).thenReturn(expected)




        val actual = useCase(email, password)

        assertTrue(actual.isSuccess)
        assertEquals(expected, actual)
        verify(userRepo).loginUserByEmailPass(email, password)
    }

    @Test
    fun `should throw exception if password is less than 8 characters`() = runTest{

        val email = "test@gmail.com"
        val password = "123"
        val expected = "Password is too short"

        val actual = useCase(email, password)

        assertTrue(actual.isFailure)
        val exception = actual.exceptionOrNull()
        assertNotNull(exception)
        assertTrue(exception is IllegalArgumentException)
        assertEquals(expected, exception?.message)

    }

    @Test
    fun `should throw exception if password or email is blank`() = runTest{
        val email = ""
        val password = "12345678"
        val expected = "Email and password can not be empty!"

        val actual = useCase(email, password)

        assertTrue(actual.isFailure)
        val exception = actual.exceptionOrNull()
        assertNotNull(exception)
        assertTrue(exception is IllegalArgumentException)
        assertEquals(expected, exception?.message)

    }

    @Test
    fun `should throw exception if email is badly formatted`() = runTest{
        val email = "test_email.com"
        val password = "12345678"
        val expected = "Invalid email"

        val actual = useCase(email, password)

        assertTrue(actual.isFailure)
        val exception = actual.exceptionOrNull()
        assertNotNull(exception)
        assertTrue(exception is IllegalArgumentException)
        assertEquals(expected, exception?.message)

    }
}