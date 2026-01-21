package com.example.bookstorev2.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookstorev2.R
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.presentation.ui.state.LoginMainNavigation
import com.example.bookstorev2.presentation.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSuccess: (User) -> Unit = {},


    ) {

    val uiState = viewModel.uiState.value


    LaunchedEffect(key1 = uiState.user) {
        when(val event = uiState.user){
            is User ->{
                onSuccess(uiState.user)
                Log.d("appvm", "Data is successfully sent to onSuccess: ${uiState.user}")

            }
            null -> Unit
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Welcome")}
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(paddingValues)
                .paint(
                    painterResource(id = R.drawable.background_login_screen),
                    contentScale = ContentScale.FillBounds
                )
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (!uiState.error.isNullOrEmpty()) {
                    Text(
                        text = uiState.error,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                OutlinedButton(
                    onClick = { viewModel.onLoginClick() },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text(text = "Log in", color = Color.Black)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = { viewModel.onRegisterClick() },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text(text = "Register", color = Color.Black)
                    }
                }


            }
        }

    }
}





