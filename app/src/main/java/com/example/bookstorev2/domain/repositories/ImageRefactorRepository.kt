package com.example.bookstorev2.domain.repositories

import android.net.Uri

interface ImageRefactorRepository {
    suspend fun uriToBase64(uri: Uri): String?
    suspend fun getFileSize(uri: Uri): Long
}