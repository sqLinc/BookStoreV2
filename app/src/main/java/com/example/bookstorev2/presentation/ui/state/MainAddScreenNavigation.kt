package com.example.bookstorev2.presentation.ui.state

import android.os.Parcelable
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.presentation.navigation.onSavedSuccess
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MainAddScreenNavigation : Parcelable {
        @Parcelize
        data class NavigateOnSaved(val book: Book) : MainAddScreenNavigation()
}