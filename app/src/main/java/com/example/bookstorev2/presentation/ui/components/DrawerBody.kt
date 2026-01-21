package com.example.bookstorev2.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerBody(

    isAdminState: Boolean,
    onAdminClick: () -> Unit = {},
    onCategoryClick: (String) -> Unit,
    onLogoutClick: () -> Unit,
    scope: CoroutineScope,
    drawerState: DrawerState






) {

    val scrollState = rememberScrollState()

    val categoryList = listOf(
        "All",
        "Favorite",
        "Read",
        "Fantasy",
        "Detective",
        "Thriller",
        "Drama",
        "Biopic",
        "Adventures"

    )

    Box(modifier = Modifier.fillMaxSize().background(Color.Gray)){
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Categories",
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))



            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                items(categoryList){ item ->

                        Column(modifier = Modifier.fillMaxWidth().clickable {
                            onCategoryClick(item)
                        }) {

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = item,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth().wrapContentWidth()
                            )
                            Spacer(modifier = Modifier.height(12.dp).size(1.dp))
                            HorizontalDivider()
                            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))
                        }


                }
            }
            if (isAdminState) ExtendedFloatingActionButton (

                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(0.6f),
                onClick = {

                    onAdminClick()
                    scope.launch {
                        drawerState.close()
                    }
                },
                icon = {Icon(Icons.Default.Add, "Add New Book")},
                text = {Text("Add New Book")},
                containerColor = Color.LightGray
            )

            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                onClick = onLogoutClick,
                text = {Text("Log out")},
                icon = {Icon(Icons.AutoMirrored.Filled.ExitToApp, "Log out")},
                containerColor = Color.LightGray
            )


        }
    }


}