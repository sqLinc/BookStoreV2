package com.example.bookstorev2.domain.usecases

import com.example.bookstorev2.domain.models.AppLanguage
import com.example.bookstorev2.domain.repositories.SettingsRepository
import javax.inject.Inject

class ChooseLanguageUseCase @Inject constructor(
    private val settingsRepo: SettingsRepository
) {
    suspend operator fun invoke(language: AppLanguage){
        settingsRepo.saveLanguage(language)
    }
}