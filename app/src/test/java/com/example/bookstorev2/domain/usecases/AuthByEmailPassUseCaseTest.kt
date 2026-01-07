package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.presentation.navigation.ToMainScreenDataObject
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock



class AuthByEmailPassUseCaseTest {

    val userRepo = mock<UserRepository>()

    @Test
    fun `should return the same valid email and uid as in repository`() = runTest{


        val testData = Result.success(ToMainScreenDataObject("test_uid", "test@gmail.com"))

        Mockito.`when`(userRepo.loginUserByEmailPass("test@gmail.com", "test_password")).thenReturn(testData)

        val useCase = AuthByEmailPassUseCase(userRepo = userRepo)

        val expected = ToMainScreenDataObject("test_uid", "test@gmail.com")
        val actual = useCase("test@gmail.com", "test_password")

        assertTrue(actual.isSuccess)
        assertEquals(expected, actual.getOrNull())
    }

    @Test
    fun `should throw exception if password is less than 8 characters`() = runTest{

        val email = "test@gmail.com"
        val password = "123"


        val useCase = AuthByEmailPassUseCase(userRepo = userRepo)

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

        val useCase = AuthByEmailPassUseCase(userRepo = userRepo)

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

        val useCase = AuthByEmailPassUseCase(userRepo = userRepo)

        val expected = "Invalid email"
        val actual = useCase(email, password)

        assertTrue(actual.isFailure)
        val exception = actual.exceptionOrNull()
        assertNotNull(exception)
        assertTrue(exception is IllegalArgumentException)
        assertEquals(expected, exception?.message)

    }
}