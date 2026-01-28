package com.example.bookstorev2.presentation.navigation

import android.os.Parcelable
import com.example.bookstorev2.domain.models.Book
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MainAddScreenNavigation : Parcelable {
        @Parcelize
        data class NavigateOnSaved(val book: Book) : MainAddScreenNavigation()
}