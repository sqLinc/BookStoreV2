package com.example.bookstorev2.data.repositories

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Base64
import com.example.bookstorev2.domain.repositories.ImageRefactorRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ImageRefactor @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageRefactorRepository{

    private val cv = context.contentResolver

    override suspend fun uriToBase64(uri: Uri): String {
        val inputStream = cv.openInputStream(uri)

        val bytes = inputStream?.readBytes()
        return bytes?.let {
            Base64.encodeToString(it, Base64.DEFAULT)
        } ?: ""
    }

}