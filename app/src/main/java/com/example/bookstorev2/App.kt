package com.example.bookstorev2

import android.app.Application
import com.example.bookstorev2.di.DatabaseEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val db = EntryPointAccessors.fromApplication(
            this,
            DatabaseEntryPoint::class.java
        ).appDatabase()




    }

}
