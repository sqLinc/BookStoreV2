package com.example.bookstorev2.presentation.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.presentation.navigation.onSavedSuccess
import com.example.bookstorev2.presentation.ui.components.ActionButton
import com.example.bookstorev2.presentation.ui.components.CategoryDropDownMenu
import com.example.bookstorev2.presentation.ui.components.DialogBody
import com.example.bookstorev2.presentation.ui.state.MainAddScreenNavigation
import com.example.bookstorev2.presentation.viewmodels.AddBookViewModel
import com.example.bookstorev2.presentation.viewmodels.AppViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun AddBookScreen(
    addBookViewModel: AddBookViewModel = hiltViewModel(),
    bookId: String,
    onSuccess: (Book?) -> Unit = {},
    onBackClick: () -> Unit = {},
    navController: NavController,
    appViewModel: AppViewModel = hiltViewModel()
) {

    val user by appViewModel.user.collectAsState()


    val openAlertDialog = remember { mutableStateOf(false) }
    val uiState by addBookViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState.savedBook) {
        when(val event = uiState.savedBook){
            is Book -> {
                onSuccess(uiState.savedBook)
                addBookViewModel.onNavigationConsumed()

            }

            null -> Unit

        }

    }
    LaunchedEffect(bookId) {
        if (bookId.isNotBlank()) {
            addBookViewModel.onBookIdUpdate(bookId)
        }
    }




    val imageBitMap = remember {
        var bitmap: Bitmap? = null
        val base64Image = Base64.decode(uiState.imageUrl, Base64.DEFAULT)
        bitmap = BitmapFactory.decodeByteArray(base64Image, 0 , base64Image.size)
        mutableStateOf(bitmap)
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageBitMap.value = null
        uiState.selectedImageUri = uri
        addBookViewModel.onImageUrlChange(uri.toString())
        addBookViewModel.onSelectedUriChange(uri)



    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adding new book") },
            )
        },



    ) { paddingValues ->

        GlideImage(
            model = imageBitMap.value ?: uiState.selectedImageUri, contentDescription = "Book image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 100.dp, end = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            ExtendedFloatingActionButton(
//                onClick = {
//                    onBackClick()
//                },
//                modifier = Modifier.padding(16.dp),
//                icon = {Icon(Icons.Default.ArrowBack, "Cancel adding new book")},
//                text = {Text("Cancel")}
//            )


            Text(
                text = "Add new book",
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )


            Spacer(modifier = Modifier.height(16.dp))

            CategoryDropDownMenu(
                uiState.category,
                onOptionSelected = { selected ->
                    addBookViewModel.onCategoryChange(selected)

                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.title,
                label = {Text(text = "Title")},
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {addBookViewModel.onTitleChange(it)}
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.price,
                label = {Text(text = "Price")},
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {addBookViewModel.onPriceChange(it)}
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.description,
                label = {Text(text = "Description")},
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {addBookViewModel.onDescriptionChange(it)}
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.author,
                label = {Text(text = "Author")},
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {addBookViewModel.onAuthorChange(it)}
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.date,
                label = {Text(text = "Published year")},
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {addBookViewModel.onDateChange(it)}
            )
            if (uiState.error.isNotEmpty()){
                Text(
                    text = uiState.error,
                    color = Color.Red,
                    textAlign = TextAlign.Center

                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(
                "Choose book cover"
            ){
                addBookViewModel.onChooseImage(imagePickerLauncher)

            }
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(
                "Save book"
            ){
                addBookViewModel.onSaveClick()
//                uiState.savedBook?.let { book ->
//                    navController.previousBackStackEntry
//                        ?.savedStateHandle
//                        ?.set("new_book", book)
//                    navController.popBackStack()
//                }

            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { openAlertDialog.value = true },
                modifier = Modifier.fillMaxWidth(0.6f).padding(bottom = 1.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
            )
            ) {
                Text(text = "Cancel")
            }
            when{
                openAlertDialog.value ->
                    DialogBody(
                        onDismiss = {openAlertDialog.value = false},
                        onConfirm = {
                            openAlertDialog.value = false
                            onBackClick()
                        },
                        dialogTitle = "Do you want to stop adding new book?",
                        dialogText = "Confirm  by pressing 'Confirm'. If you want to continue to add book press 'Dismiss'",
                        icon = Icons.Default.Info
                    )
            }



        }
    }
}