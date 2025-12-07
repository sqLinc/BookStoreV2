package com.example.bookstorev2.presentation.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.bookstorev2.domain.models.Book

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookItem(
    book: Book,
    onFavoriteClick: () -> Unit,
    onReadClick: () -> Unit,
    modifier: Modifier = Modifier
    ) {
    Card(
        modifier = modifier.padding(8.dp)
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            val base64Image = android.util.Base64.decode(book.imageUrl, android.util.Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(base64Image, 0, base64Image.size)

            GlideImage(
                model = bitmap ?: "", contentDescription = "BG",
                modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(15.dp))

            )
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyMedium
            )

            Row{
                IconButton(onClick = onFavoriteClick){
                    Icon(
                        imageVector = if (book.isFavorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.Favorite,
                        contentDescription = "Favorite",
                        tint = if (book.isFavorite) Color.Red else Color.Gray
                    )
                }
                IconButton(onClick = onReadClick){
                    Icon(
                        imageVector = if (book.isRead)
                            Icons.Filled.Done
                        else
                            Icons.Outlined.Done,
                        contentDescription = "Read",
                        tint = if (book.isRead) Color.Green else Color.Gray
                    )
                }
            }
        }
    }
}