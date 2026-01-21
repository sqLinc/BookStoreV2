plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    kotlin("plugin.serialization") version "1.9.24" apply false // ОБНОВИТЬ
    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
    kotlin("jvm") version "1.9.24" apply false // ОБНОВИТЬ

}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24")
    }
}