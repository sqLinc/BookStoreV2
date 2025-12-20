package com.example.bookstorev2.presentation.ui.screens

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.bookstorev2.presentation.viewmodels.BookDetailViewModel


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(

    detailViewModel: BookDetailViewModel = hiltViewModel(),
    bookId: String,
    onBackClick: () -> Unit = {},
) {

    val uiState = detailViewModel.uiState.value

    LaunchedEffect(Unit) {
        detailViewModel.getBook(bookId)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        val base64Image = android.util.Base64.decode(uiState.imageUrl, android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(base64Image, 0, base64Image.size)

        IconButton(onClick = onBackClick){
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = ""

            )
        }
        GlideImage(
            model = bitmap ?: Text("Image is not existed"), contentDescription = "BG",
            modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(15.dp))
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ){
            Text(
                text = uiState.title,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        }

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Автор: ${uiState.author}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Год издания: ${uiState.date}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Цена: ${uiState.price}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Категория: ${uiState.category}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = uiState.description,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )


            }
        }


    }
}