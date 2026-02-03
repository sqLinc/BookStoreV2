package com.example.bookstorev2.presentation.viewmodels.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.data.repositories.SettingsRepository
import com.example.bookstorev2.domain.repositories.BookRepository
import com.example.bookstorev2.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepo: SettingsRepository,
    private val bookRepo: BookRepository,
    private val userRepo: UserRepository
) : ViewModel() {

    val isDarkTheme = settingsRepo.isDarkThemeFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    val language = settingsRepo.language.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "en"
    )
    val uid = settingsRepo.uid.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )
    val email = settingsRepo.email.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    fun setDark(value: Boolean) {
        viewModelScope.launch {
            settingsRepo.setDarkTheme(value)
        }
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            settingsRepo.setLanguage(lang)
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            settingsRepo.deleteUser()
        }
    }

    fun onCacheDelete() {
        viewModelScope.launch {
            bookRepo.deleteAllFromLocal()
        }
    }

    fun onAccountDelete() {
        viewModelScope.launch {
            userRepo.deleteAccount()
        }
    }
}
