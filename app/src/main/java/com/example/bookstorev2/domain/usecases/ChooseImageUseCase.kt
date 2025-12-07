package com.example.bookstorev2.domain.usecases

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import com.example.bookstorev2.domain.repositories.BookRepository
import javax.inject.Inject

class ChooseImageUseCase @Inject constructor(
    private val bookRepo: BookRepository
) {
    suspend operator fun invoke(imagePickerLauncher: ManagedActivityResultLauncher<String, Uri?>){
        bookRepo.chooseImage(imagePickerLauncher)
    }
}