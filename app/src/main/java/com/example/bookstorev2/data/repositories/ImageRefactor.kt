package com.example.bookstorev2.data.repositories

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Base64
import androidx.compose.ui.platform.LocalContext
import com.example.bookstorev2.domain.repositories.ImageRefactorRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRefactor @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageRefactorRepository {

    private val cv = context.contentResolver


    override suspend fun uriToBase64(uri: Uri): String {
        return withContext(Dispatchers.IO) {
            try {
                // Дать разрешение на чтение URI (для документов, поддерживающих persistable permissions)
                if (uri.scheme == "content") {
                    try {
                        context.contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    } catch (e: SecurityException) {
                        // Иногда может бросить SecurityException — безопасно игнорировать
                    }
                }

                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val bytes = inputStream.readBytes()
                    Base64.encodeToString(bytes, Base64.DEFAULT)
                } ?: ""
            } catch (e: Exception) {
                ""
            }
        }
    }
}