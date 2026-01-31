package com.example.bookstorev2.data.repositories

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import com.example.bookstorev2.domain.repositories.ImageRefactorRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRefactorRepositoryImpl(
    @ApplicationContext private val context: Context
) : ImageRefactorRepository {

    override suspend fun uriToBase64(uri: Uri): String? = withContext(Dispatchers.IO) {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val bytes = inputStream.readBytes()
            Base64.encodeToString(bytes, Base64.DEFAULT)
        }
    }

    override suspend fun getFileSize(uri: Uri): Long {
        return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (cursor.moveToFirst() && sizeIndex != -1) {
                cursor.getLong(sizeIndex)
            } else 0L
        } ?: 0L
    }
}

