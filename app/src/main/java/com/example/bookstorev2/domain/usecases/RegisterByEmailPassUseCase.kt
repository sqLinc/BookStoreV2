package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.presentation.navigation.ToMainScreenDataObject
import javax.inject.Inject

class RegisterByEmailPassUseCase @Inject constructor(
    private val userRepo: UserRepository
) {
    suspend operator fun invoke(email: String, password: String) : Result<ToMainScreenDataObject>{
        if(email.isBlank() || password.isBlank()){
            return Result.failure(IllegalArgumentException("Email and password can not be empty!"))
        }
        if(email.length < 6 || !email.contains("@")){
            return Result.failure((IllegalArgumentException("Invalid email")))
        }
        if (password.length < 8){
            return Result.failure((IllegalArgumentException("Password is too short")))
        }

        else{
            return userRepo.createUserByEmailPass(email, password)
        }



    }
}