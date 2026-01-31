package com.example.bookstorev2.domain.repositories

import android.content.Context
import androidx.activity.result.ActivityResult
import com.example.bookstorev2.domain.models.User
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    suspend fun isAdmin(): Boolean
    suspend fun loginUserByEmailPass(email: String, password: String): Result<User>
    suspend fun createUserByEmailPass(email: String, password: String): Result<User>
    suspend fun loginByGoogle(context: Context, clientId: String): Result<GoogleSignInClient>
    suspend fun googleLauncher(result: ActivityResult): Result<FirebaseUser>
    suspend fun deleteAccount()
}