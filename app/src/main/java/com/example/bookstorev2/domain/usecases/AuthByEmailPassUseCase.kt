package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.presentation.navigation.ToMainScreenDataObject
import javax.inject.Inject

class AuthByEmailPassUseCase @Inject constructor(
    private val userRepo: UserRepository

) {
    suspend operator fun invoke(email: String, password: String) : Result<ToMainScreenDataObject>{
        return userRepo.loginUserByEmailPass(email, password)
    }
}