package com.example.bookstorev2.domain.repositories

import com.example.bookstorev2.domain.models.AppLanguage
import com.example.bookstorev2.domain.models.AppTheme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun saveLanguage(language: AppLanguage)
    suspend fun saveTheme(theme: AppTheme)

    fun getCurrentLanguage() : Flow<AppLanguage>
    fun getCurrentTheme() : Flow<AppTheme>
}