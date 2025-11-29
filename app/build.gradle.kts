plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.bookstorev2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bookstorev2"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // build.gradle.kts (Module)
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")
    // build.gradle (Module)
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    // Hilt
    implementation ("com.google.dagger:hilt-android:2.48")
    implementation ("com.google.dagger:hilt-compiler:2.48")

    // Hilt для ViewModel
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")


    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Firebase (using the BOM from libs.versions.toml)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.common.ktx)
    implementation(libs.firebase.storage)
    implementation("com.google.firebase:firebase-auth")

    // Android Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose (using the BOM from libs.versions.toml)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.4")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}