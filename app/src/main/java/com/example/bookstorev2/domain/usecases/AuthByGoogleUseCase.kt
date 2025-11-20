package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.repositories.UserRepository
import javax.inject.Inject

class AuthByGoogleUseCase @Inject constructor(
    private val userRepo: UserRepository
) {
    suspend operator fun invoke(idToken: String){
        userRepo.loginByGoogle(idToken)
    }
}