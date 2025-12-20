package com.example.bookstorev2.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookstorev2.presentation.viewmodels.AddBookViewModel
import kotlin.math.exp

@Composable
fun CategoryDropDownMenu(
    selectedCategory: String,
    onOptionSelected: (String) -> Unit,

) {


    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(selectedCategory) }
    val categories = listOf (
        "Fantasy",
        "Detective",
        "Thriller",
        "Drama",
        "Biopic",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Gray)
            .background(Color.White)
            .clickable { expanded.value = !expanded.value }
            .padding(15.dp)
    ){
        Text(text = selectedOption.value)
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {expanded.value = false}
        ){
            categories.forEach{ option ->
                DropdownMenuItem(text = {
                    Text(text = option)
                }, onClick = {


                    onOptionSelected(option)
                    selectedOption.value = option
                    expanded.value = false

                })
            }
        }

    }
}