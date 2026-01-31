package com.example.bookstorev2.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object PreferencesKeys {
    val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    val LANGUAGE = stringPreferencesKey("language")
    val UID = stringPreferencesKey("uid")
    val EMAIL = stringPreferencesKey("email")
}


class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val isDarkThemeFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_DARK_THEME] ?: false
        }

    suspend fun setDarkTheme(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_THEME] = isDark
        }
    }

    val language: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.LANGUAGE] ?: "en"
        }

    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LANGUAGE] = language
        }
    }

    suspend fun setUid(uid: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.UID] = uid
        }
    }

    suspend fun setEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EMAIL] = email
        }
    }

    val uid: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.UID] ?: ""
        }
    val email: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.EMAIL] ?: ""

        }

    suspend fun deleteUser() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.UID] = ""
            preferences[PreferencesKeys.EMAIL] = ""
            preferences[PreferencesKeys.LANGUAGE] = "en"
            preferences[PreferencesKeys.IS_DARK_THEME] = false
        }
    }
}
