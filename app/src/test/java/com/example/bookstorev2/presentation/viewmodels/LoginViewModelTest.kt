package com.example.bookstorev2.presentation.viewmodels

import com.example.bookstorev2.data.repositories.SettingsRepository
import com.example.bookstorev2.dispatcher.MainDispatcherRule
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.domain.usecases.AuthByEmailPassUseCase
import com.example.bookstorev2.domain.usecases.RegisterByEmailPassUseCase
import com.example.bookstorev2.presentation.viewmodels.viewmodels.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var userRepo: UserRepository
    private lateinit var settingsRepo: SettingsRepository
    private lateinit var authUseCase: AuthByEmailPassUseCase
    private lateinit var registerUseCase: RegisterByEmailPassUseCase
    private lateinit var viewModel: LoginViewModel


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Before
    fun setUp() {
        userRepo = mock()
        settingsRepo = mock()
        authUseCase = AuthByEmailPassUseCase(userRepo)
        registerUseCase = RegisterByEmailPassUseCase(userRepo)

        viewModel = LoginViewModel(
            authUseCase,
            registerUseCase,
            userRepo,
            settingsRepo
        )
    }

    @Test
    fun admin_check_updates_ui_state_value_of_isAdminState() = runTest {

        whenever(userRepo.isAdmin()).thenReturn(true)
        viewModel.onAdminCheck()
        assertTrue(viewModel.uiState.value.isAdminState)
        verify(userRepo).isAdmin()
    }

    @Test
    fun onEmailChange_updates_ui_state_value_of_email() = runTest {
        val email = "test@gmail.com"
        viewModel.onEmailChange(email)
        assertEquals(email, viewModel.uiState.value.email)
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun onPasswordChange_updates_ui_state_value_of_password() = runTest {
        val password = "12345678"
        viewModel.onPasswordChange(password)
        assertEquals(password, viewModel.uiState.value.password)
        assertNull(viewModel.uiState.value.error)
    }


    @Test
    fun onLoginClick_should_update_ui_state_value_of_error_if_failure() = runTest {
        val email = ""
        val password = ""
        val failureResult =
            Result.failure<User>(IllegalArgumentException("Email and password can not be empty!"))

        whenever(userRepo.loginUserByEmailPass(any(), any())).thenReturn(failureResult)

        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        viewModel.onLoginClick()
        advanceUntilIdle()

        val error = failureResult.exceptionOrNull()
        assertEquals(error?.message, viewModel.uiState.value.error)
    }


    @Test
    fun onRegisterClick_should_update_ui_state_value_of_error_if_failure() = runTest {
        val email = ""
        val password = ""
        val failureResult =
            Result.failure<User>(IllegalArgumentException("Email and password can not be empty!"))

        whenever(userRepo.createUserByEmailPass(any(), any())).thenReturn(failureResult)

        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)
        viewModel.onRegisterClick()
        advanceUntilIdle()

        val error = failureResult.exceptionOrNull()
        assertEquals(error?.message, viewModel.uiState.value.error)
    }
}