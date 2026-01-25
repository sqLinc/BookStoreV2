package com.example.bookstorev2.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstorev2.data.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: SettingsRepository
) : ViewModel() {

    val isDarkTheme = repo.isDarkThemeFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    val language = repo.language.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "en"
    )
    val uid = repo.uid.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )
    val email = repo.email.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    fun setDark(value: Boolean) {
        viewModelScope.launch {
            repo.setDarkTheme(value)
        }
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            repo.setLanguage(lang)
        }
    }
    fun deleteUser(){
        viewModelScope.launch {
            repo.deleteUser()
        }
    }
    fun setEmail(email: String){
        viewModelScope.launch {
            repo.setEmail(email)
        }
    }
    fun setUid(uid: String){
        viewModelScope.launch {
            repo.setUid(uid)
        }
    }
}
