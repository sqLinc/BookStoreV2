package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.models.AppTheme
import com.example.bookstorev2.domain.repositories.SettingsRepository
import javax.inject.Inject

class ChooseThemeUseCase @Inject constructor(
    private val settingsRepo: SettingsRepository
) {
    suspend operator fun invoke(theme: AppTheme){
        settingsRepo.saveTheme(theme)
    }
}