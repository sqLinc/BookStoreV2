package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.repositories.UserRepository
import javax.inject.Inject

class AuthByEmailPassUseCase @Inject constructor(
    private val userRepo: UserRepository
) {
    suspend operator fun invoke(email: String, password: String){
        userRepo.loginUserByEmailPass(email, password)
    }
}