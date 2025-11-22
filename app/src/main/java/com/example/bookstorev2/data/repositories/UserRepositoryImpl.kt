package com.example.bookstorev2.data.repositories

import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.presentation.navigation.MainScreenDataObject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : UserRepository {
    override suspend fun loginUserByEmailPass(email: String, password: String) : MainScreenDataObject{
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await().user
            MainScreenDataObject(
                result!!.uid,
                result.email.toString()
            )
        } catch (e: Exception){
            throw e
        }


    }

    override suspend fun createUserByEmailPass(email: String, password: String): MainScreenDataObject {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await().user
            MainScreenDataObject(
                result!!.uid,
                result.email.toString()
            )
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun loginByGoogle(idToken: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    /*override fun getCurrentUser(): MainScreenDataObject {
        return try {
            val user = Firebase.auth.currentUser
            val uid = user!!.uid
            val email = user.email
            MainScreenDataObject(uid, email!!)

        } catch (e: Exception){
            throw e
        }
    }*/




    override suspend fun logOut() {
        TODO("Not yet implemented")
    }

}
