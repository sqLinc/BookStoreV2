package com.example.bookstorev2.presentation.ui.screens

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule  // Измените импорт
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bookstorev2.HiltTestActivity  // Импорт новой Activity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltTestActivity>()  // Изменение здесь

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun emailTextField_changesText() {
        composeRule.setContent {
            LoginScreen()
        }

        composeRule
            .onNodeWithTag("email_field")
            .performTextInput("test@example.com")

        composeRule
            .onNodeWithTag("email_field")
            .assertTextContains("test@example.com")
    }
}