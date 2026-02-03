package com.example.bookstorev2.presentation.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bookstorev2.MainActivity
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.presentation.viewmodels.fake_viewmodels.FakeLoginViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()


    lateinit var fakeVm: FakeLoginViewModel

    @Before
    fun setUp() {
        fakeVm = FakeLoginViewModel()

    }



    @Test
    fun login_success_calls_onSuccess() {
        val user = User("test_uid", "test_email")
        fakeVm.loginResult = Result.success(user)

        var successUser: User? = null

        composeRule.setContent {
            LoginScreen(viewModel = fakeVm) {
                successUser = it
            }

        }

        composeRule.onNodeWithTag("email")
            .performTextInput("test_email")

        composeRule.onNodeWithTag("password")
            .performTextInput("test_password")

        composeRule.onNodeWithTag("login")
            .performClick()

        composeRule.waitForIdle()

        Assert.assertEquals(user, successUser)
    }


}