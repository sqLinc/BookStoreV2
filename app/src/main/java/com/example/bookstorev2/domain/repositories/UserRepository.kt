package com.example.bookstorev2.domain.repositories

import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.presentation.navigation.ToMainScreenDataObject

interface UserRepository {
    suspend fun isAdmin() : Boolean
    suspend fun loginUserByEmailPass(email: String, password: String) : Result<User>
    suspend fun createUserByEmailPass(email: String, password: String) : Result<User>
    suspend fun loginByGoogle(idToken : String) : Result<ToMainScreenDataObject>

    //fun getCurrentUser() : MainScreenDataObject
    suspend fun logOut()
    suspend fun onCreatingUser(uid: String)
}