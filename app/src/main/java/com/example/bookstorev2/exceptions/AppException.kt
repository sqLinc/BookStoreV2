package com.example.bookstorev2.exceptions

import com.example.bookstorev2.R

sealed class AppException(
    open val messageRes: Int? = null,
    open val rawMessage: String? = null
) : Exception() {

    sealed class Auth(
        override val messageRes: Int? = null,
        override val rawMessage: String? = null
    ) : AppException(messageRes, rawMessage) {

        class InvalidEmail : Auth(R.string.error_invalid_email)
        class UserNotFound : Auth(R.string.error_user_not_found)
        class WrongPassword : Auth(R.string.error_wrong_password)
        class EmailAlreadyUsed : Auth(R.string.error_email_used)
        class WeakPassword : Auth(R.string.error_weak_password)

        data class Unknown(val code: String) : Auth(rawMessage = code)

    }

    sealed class Firestore(
        override val messageRes: Int? = null,
        override val rawMessage: String? = null
    ) : AppException(messageRes, rawMessage) {

        class PermissionDenied : Firestore(R.string.error_permission)
        class NotFound : Firestore(R.string.error_not_found)
        class Unavailable : Firestore(R.string.error_network)

        data class Unknown(val code: String) : Firestore(rawMessage = code)

    }

    sealed class Security(
        override val messageRes: Int? = null,
        override val rawMessage: String? = null
    ) : AppException(messageRes, rawMessage) {

        class SecurityException : Security(R.string.error_security)
    }

    sealed class Unknown(
        override val messageRes: Int? = null,
        override val rawMessage: String? = null
    ) : AppException(messageRes, rawMessage) {

        class UnknownException : Unknown(R.string.error_unknown)
    }


}