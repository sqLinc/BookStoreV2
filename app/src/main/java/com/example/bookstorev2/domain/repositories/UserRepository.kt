package com.example.bookstorev2.domain.repositories

import com.example.bookstorev2.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun loginUserByEmailPass(email: String, password: String) : Result<Unit>
    suspend fun createUserByEmailPass(email: String, password: String) : Result<Unit>
    suspend fun loginByGoogle(idToken : String) : Result<Unit>

    fun getCurrentUser() : Flow<User?>
    suspend fun logOut()
}