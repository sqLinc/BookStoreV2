package com.example.bookstorev2.ui.theme

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

// функция изменения языка приложения

lateinit var default: Locale

fun setLocale(activity: Activity, lang: String){
    val locale = Locale(lang)
    Locale.setDefault(locale)

    val config = Configuration(activity.resources.configuration)
    config.setLocale(locale)

    activity.resources.updateConfiguration(
        config,
        activity.resources.displayMetrics
    )
}