package com.example.bookstorev2.presentation.viewmodels

import androidx.compose.runtime.traceEventStart
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.domain.usecases.AuthByEmailPassUseCase
import com.example.bookstorev2.domain.usecases.RegisterByEmailPassUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LoginViewModelTest {

    private lateinit var userRepo: UserRepository
    private lateinit var authUseCase: AuthByEmailPassUseCase
    private lateinit var registerUseCase: RegisterByEmailPassUseCase
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp(){
        userRepo = mock()
        authUseCase = AuthByEmailPassUseCase(userRepo)
        registerUseCase = RegisterByEmailPassUseCase(userRepo)

        viewModel = LoginViewModel(
            authUseCase,
            registerUseCase,
            userRepo
        )
    }

    @Test
    fun `admin check updates value of ui state of isAdminState`() = runTest{

        whenever(userRepo.isAdmin()).thenReturn(true)
        viewModel.onAdminCheck()
        assertTrue(viewModel.uiState.value.isAdminState)
        verify(userRepo).isAdmin()
    }

}