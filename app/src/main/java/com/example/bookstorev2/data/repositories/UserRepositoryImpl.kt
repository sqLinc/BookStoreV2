package com.example.bookstorev2.data.repositories

import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.presentation.navigation.ToMainScreenDataObject
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : UserRepository {
    override suspend fun loginUserByEmailPass(email: String, password: String) : Result<ToMainScreenDataObject>{
        return try {

            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Result.failure(IllegalArgumentException("This user does not exist!"))

            Result.success(
                ToMainScreenDataObject(
                    uid = user.uid,
                    email = user.email ?: ""
                )
            )

        } catch (e: Exception){
            val exception = when {
                e is FirebaseTooManyRequestsException -> ( "Too many login attempts. Try again later" )
                e is FirebaseAuthInvalidUserException -> ( "User is not found" )
                e is FirebaseNetworkException -> ( "Bad Internet connection" )

                else -> e.message ?: "Error"
            }

            Result.failure(Exception(exception))
        }


    }

    override suspend fun createUserByEmailPass(email: String, password: String): Result<ToMainScreenDataObject>{
        return try {



            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Result.failure(IllegalArgumentException("Can not create user!"))


            return Result.success(
                ToMainScreenDataObject(
                    uid = user.uid,
                    email = user.email ?: ""
                )
            )
        } catch (e: Exception){
            val exception = when {
                e is FirebaseTooManyRequestsException -> ( "Too many login attempts. Try again later" )
                e is FirebaseAuthInvalidUserException -> ( "User is not found" )
                e is FirebaseNetworkException -> ( "Bad Internet connection" )
                e is FirebaseAuthWeakPasswordException -> ( "Password should be at least 6 characters" )

                else -> e.message ?: "Error"
            }

            Result.failure(Exception(exception))
        }
    }

    override suspend fun loginByGoogle(idToken: String): Result<ToMainScreenDataObject> {
        TODO("Not yet implemented")
    }

    override suspend fun logOut() {
        TODO("Not yet implemented")
    }

    override suspend fun isAdmin(): Boolean {
        return Firebase.firestore.collection("admin").document(Firebase.auth.currentUser!!.uid).get().await()
            .getBoolean("isAdmin") ?: false

    }

}
