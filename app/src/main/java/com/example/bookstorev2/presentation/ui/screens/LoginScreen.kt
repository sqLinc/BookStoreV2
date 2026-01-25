package com.example.bookstorev2.presentation.ui.screens

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookstorev2.R
import com.example.bookstorev2.domain.models.User
import com.example.bookstorev2.presentation.ui.state.LoginMainNavigation
import com.example.bookstorev2.presentation.viewmodels.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSuccess: (User) -> Unit = {},



    ) {

    val uiState = viewModel.uiState.value
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("GoogleAuth", "Got result from launcher")
        if (result.resultCode == Activity.RESULT_OK) {
            Log.d("GoogleAuth", "result code is RESULT_OK")
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            Log.d("GoogleAuth", "Got task")
            val account = task.getResult(ApiException::class.java)
            Log.d("GoogleAuth", "Got account")
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            Log.d("GoogleAuth", "Trying to firebase auth")
            FirebaseAuth.getInstance()
                .signInWithCredential(credential)
                .addOnSuccessListener { authResult ->
                    val user = authResult.user
                    Log.d("GoogleAuth", "Success!!!")
                    viewModel.onGoogleSuccess(user!!)
                    Log.d("GoogleAuth", "Success!!!")


                }
                .addOnFailureListener {
                    Log.d("GoogleAuth", it.message ?: "error")
                }
        }
        else{
            Log.d("GoogleAuth", "Result is not OK")
        }
    }


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
                title = { Text(stringResource(R.string.login_top_bar))}
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
                    label = { stringResource(R.string.email_field) },
                    modifier = Modifier.fillMaxWidth(0.8f).testTag("email_field")
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { stringResource(R.string.password_field) },
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
                        Text(text = stringResource(R.string.login_button), color = Color.Black)
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
                        Text(text = stringResource(R.string.register_button), color = Color.Black)
                    }
                }
                OutlinedButton(
                    onClick = {
                        Log.d("GoogleAuth", "Clicked google button")
                        val client = viewModel.getGoogleSignInClient(context, context.getString(R.string.default_web_client_id) )
                        Log.d("GoogleAuth", "Got Client")
                        launcher.launch(client.signInIntent)
                    },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    enabled = !uiState.isLoading
                ) {
                    Text(text = "Sign in with Google")
                }



            }
        }

    }
}





