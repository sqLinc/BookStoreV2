package com.example.bookstorev2.presentation.ui.components

import android.R
import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.bookstorev2.domain.models.Book
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookItem(
    isAdmin: Boolean,
    book: Book,
    onFavoriteClick: () -> Unit,
    onReadClick: () -> Unit,
    onEditClick: (String) -> Unit,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier,

) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        OutlinedCard(
            modifier = modifier.padding(8.dp)
                .fillMaxWidth(0.9f),


            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            )
        ){

            Column(
                modifier = Modifier.padding(16.dp)

            ) {
                val base64Image = android.util.Base64.decode(book.imageUrl, android.util.Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(base64Image, 0, base64Image.size)

                Card(
                    modifier = Modifier.wrapContentWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 15.dp
                    )
                ) {
                    GlideImage(
                        model = bitmap ?: "", contentDescription = "BG",
                        modifier = Modifier.fillMaxWidth().height(250.dp).clip(RoundedCornerShape(15.dp)).clickable {
                            onBookClick(book.key)
                        }


                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,

                        )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = book.author,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold


                        )

                    Text(
                        text = book.price,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold


                        )

                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
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

                    if (isAdmin) IconButton(onClick = {onEditClick(book.key)}) {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = "Edit book",
                            tint = Color.Black
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

                }
            }
        }
    }


}
