package com.example.bookstorev2.presentation.ui.components

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.bookstorev2.domain.models.Book


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookItem(
    isAdmin: Boolean,
    book: Book,
    onFavoriteClick: () -> Unit,
    onReadClick: () -> Unit,
    onEditClick: (String) -> Unit,
    onBookClick: (String) -> Unit,
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
                modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(15.dp)).clickable {
                    onBookClick(book.key)
                }


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
                        imageVector = if (book.favorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Outlined.Favorite,
                        contentDescription = "Favorite",
                        tint = if (book.favorite) Color.Red else Color.Gray
                    )
                }
                IconButton(onClick = onReadClick){
                    Icon(
                        imageVector = if (book.read)
                            Icons.Filled.Done
                        else
                            Icons.Outlined.Done,
                        contentDescription = "Read",
                        tint = if (book.read) Color.Green else Color.Gray
                    )
                }
                if (isAdmin) IconButton(onClick = {onEditClick(book.key)}, modifier = Modifier.offset(x = 250.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Edit book",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}
