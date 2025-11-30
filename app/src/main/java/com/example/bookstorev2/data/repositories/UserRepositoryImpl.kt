package com.example.bookstorev2.data.repositories

import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.presentation.navigation.ToMainScreenDataObject
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : UserRepository {
    override suspend fun loginUserByEmailPass(email: String, password: String) : Result<ToMainScreenDataObject>{
        return try {

            if(email.isBlank() || password.isBlank()){
                return Result.failure(Exception("Email and password can not be empty!"))
            }

            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Result.failure(Exception("This user does not exist!"))

            Result.success(
                ToMainScreenDataObject(
                    uid = user.uid,
                    email = user.email ?: ""
                )
            )
        } catch (e: Exception){
            Result.failure(e)
        }


    }

    override suspend fun createUserByEmailPass(email: String, password: String): Result<ToMainScreenDataObject>{
        return try {

            if(email.isBlank() || password.isBlank()){
                return Result.failure(Exception("Email and password can not be empty!"))
            }

            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Result.failure(Exception("Can not create user!"))


            return Result.success(
                ToMainScreenDataObject(
                    uid = user.uid,
                    email = user.email ?: ""
                )
            )
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun loginByGoogle(idToken: String): Result<ToMainScreenDataObject> {
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
