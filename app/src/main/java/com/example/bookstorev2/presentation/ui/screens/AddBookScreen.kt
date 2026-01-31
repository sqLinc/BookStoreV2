package com.example.bookstorev2.presentation.ui.screens

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.bookstorev2.R
import com.example.bookstorev2.domain.models.Book
import com.example.bookstorev2.presentation.ui.components.ActionButton
import com.example.bookstorev2.presentation.ui.components.CategoryDropDownMenu
import com.example.bookstorev2.presentation.ui.components.DialogBody
import com.example.bookstorev2.presentation.viewmodels.AddBookViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun AddBookScreen(
    addBookViewModel: AddBookViewModel = hiltViewModel(),
    bookId: String,
    onSuccess: (Book?) -> Unit = {},
    onBackClick: () -> Unit = {},
) {

    val openAlertDialog = remember { mutableStateOf(false) }
    val uiState by addBookViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState.savedBook) {
        when (uiState.savedBook) {
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


    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            addBookViewModel.onSelectedUriChange(uri)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { stringResource(R.string.new_book_bar_text) },
            )
        },

        ) { paddingValues ->

        val base64Image =
            android.util.Base64.decode(uiState.base64Image, android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(base64Image, 0, base64Image.size)

        GlideImage(
            model = uiState.imageUri ?: bitmap,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 100.dp, end = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.new_book_text),
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
                label = { Text(text = stringResource(R.string.new_book_title)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { addBookViewModel.onTitleChange(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.price,
                label = { Text(text = stringResource(R.string.new_book_price)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { addBookViewModel.onPriceChange(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.description,
                label = { Text(text = stringResource(R.string.new_book_description)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { addBookViewModel.onDescriptionChange(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.author,
                label = { Text(text = stringResource(R.string.new_book_author)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { addBookViewModel.onAuthorChange(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.date,
                label = { Text(text = stringResource(R.string.new_book_published)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { addBookViewModel.onDateChange(it) }
            )
            if (uiState.error.isNotEmpty()) {
                Text(
                    text = uiState.error,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(
                stringResource(R.string.new_book_choose_image)
            ) {
                imagePickerLauncher.launch(arrayOf("image/*"))

            }
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(
                stringResource(R.string.new_book_save)
            ) {
                addBookViewModel.onSaveClick()
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { openAlertDialog.value = true },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(bottom = 1.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                )
            ) {
                Text(text = stringResource(R.string.new_book_cancel))
            }
            when {
                openAlertDialog.value ->
                    DialogBody(
                        onDismiss = { openAlertDialog.value = false },
                        onConfirm = {
                            openAlertDialog.value = false
                            onBackClick()
                        },
                        dialogTitle = stringResource(R.string.new_book_dialog_top_text),
                        dialogText = stringResource(R.string.new_book_dialog_text),
                        icon = Icons.Default.Info,
                        dismissText = stringResource(R.string.button_dismiss),
                        confirmText = stringResource(R.string.button_confirm)
                    )
            }


        }
    }
}