package com.example.bookstorev2.data.repositories

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResult
import com.example.bookstorev2.domain.models.FirebaseCollections.ADMIN
import com.example.bookstorev2.domain.models.FirebaseCollections.IS_ADMIN
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.domain.repositories.UserRepository
import com.example.bookstorev2.exceptions.toAppException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : UserRepository {
    override suspend fun loginUserByEmailPass(email: String, password: String): Result<User> {
        return try {

            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
                ?: return Result.failure(IllegalArgumentException("This user does not exist!"))

            Result.success(
                User(
                    uid = user.uid,
                    email = user.email ?: ""
                )
            )

        } catch (e: Exception) {
            Result.failure(Exception(e.toAppException()))
        }
    }

    override suspend fun createUserByEmailPass(email: String, password: String): Result<User> {
        return try {

            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
                ?: return Result.failure(IllegalArgumentException("Can not create user!"))


            return Result.success(
                User(
                    uid = user.uid,
                    email = user.email!!
                )
            )
        } catch (e: Exception) {
            Result.failure(Exception(e.toAppException()))
        }
    }

    override suspend fun loginByGoogle(
        context: Context,
        clientId: String
    ): Result<GoogleSignInClient> {
        return try {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build()
            return Result.success(
                GoogleSignIn.getClient(context, gso)
            )
        } catch (e: Exception) {
            Result.failure(Exception(e.toAppException()))
        }

    }

    override suspend fun isAdmin(): Boolean {
        return Firebase
            .firestore
            .collection(ADMIN)
            .document(Firebase.auth.currentUser!!.uid)
            .get()
            .await()
            .getBoolean(IS_ADMIN) ?: false
    }

    override suspend fun googleLauncher(result: ActivityResult): Result<FirebaseUser> =
        suspendCoroutine { cont ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    val account = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance()
                        .signInWithCredential(credential)
                        .addOnSuccessListener { result ->
                            val user = result.user
                            cont.resume(Result.success(user!!))
                        }
                } else {
                    Log.d("GoogleAuth", "Result is not OK")
                }
            } catch (e: Exception) {
                throw e.toAppException()
            }


        }

    override suspend fun deleteAccount() {
        try {
            val user = Firebase.auth.currentUser!!
            user.delete()

        } catch (e: Exception) {
            throw e.toAppException()
        }
    }


}
