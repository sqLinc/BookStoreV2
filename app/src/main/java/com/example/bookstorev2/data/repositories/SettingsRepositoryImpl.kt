package com.example.bookstorev2.data.repositories

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.bookstorev2.domain.models.AppLanguage
import com.example.bookstorev2.domain.models.AppTheme
import com.example.bookstorev2.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: Enum<AppLanguage>

) : SettingsRepository {
    private object PreferencesKeys {
        val LANGUAGE = stringPreferencesKey("app_language")
        val THEME = stringPreferencesKey("app_theme")
    }

    override suspend fun saveLanguage(language: AppLanguage) {
        TODO("Not yet implemented")
    }

    override suspend fun saveTheme(theme: AppTheme) {
        TODO("Not yet implemented")
    }

    override fun getCurrentLanguage(): Flow<AppLanguage> {
        TODO("Not yet implemented")
    }

    override fun getCurrentTheme(): Flow<AppTheme> {
        TODO("Not yet implemented")
    }

}