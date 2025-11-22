package com.example.bookstorev2.domain.repositories

import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.presentation.navigation.MainScreenDataObject
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun loginUserByEmailPass(email: String, password: String) : Result<MainScreenDataObject>
    suspend fun createUserByEmailPass(email: String, password: String) : Result<MainScreenDataObject>
    suspend fun loginByGoogle(idToken : String) : Result<MainScreenDataObject>

    //fun getCurrentUser() : MainScreenDataObject
    suspend fun logOut()
}