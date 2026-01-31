package com.example.bookstorev2.exceptions

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException

fun Throwable.toAppException(): AppException =
    when (this) {
        is FirebaseAuthException -> mapAuth(this)
        is FirebaseFirestoreException -> mapFirestore(this)
        is SecurityException -> AppException.Security.SecurityException()

        else -> AppException.Unknown.UnknownException()
    }

private fun mapAuth(e: FirebaseAuthException): AppException.Auth =
    when (e.errorCode) {
        "ERROR_INVALID_EMAIL" -> AppException.Auth.InvalidEmail()
        "ERROR_USER_NOT_FOUND" -> AppException.Auth.UserNotFound()
        "ERROR_WRONG_PASSWORD" -> AppException.Auth.WrongPassword()
        "ERROR_EMAIL_ALREADY_IN_USE" -> AppException.Auth.EmailAlreadyUsed()
        "ERROR_WEAK_PASSWORD" -> AppException.Auth.WeakPassword()
        else -> AppException.Auth.Unknown(e.errorCode)
    }

private fun mapFirestore(e: FirebaseFirestoreException): AppException.Firestore =
    when (e.code) {
        FirebaseFirestoreException.Code.PERMISSION_DENIED -> AppException.Firestore.PermissionDenied()
        FirebaseFirestoreException.Code.NOT_FOUND -> AppException.Firestore.NotFound()
        FirebaseFirestoreException.Code.UNAVAILABLE -> AppException.Firestore.Unavailable()
        else -> AppException.Firestore.Unknown(e.code.name)
    }


