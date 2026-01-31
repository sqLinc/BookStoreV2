import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LangDropDownMenu(
    selectedCategory: String,
    onOptionSelected: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(selectedCategory) }
    val categories = listOf(
        "ru",
        "en"
    )

    Box(
        modifier = Modifier
            .padding(5.dp)
    ) {
        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(Icons.Default.MoreVert, "")
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            categories.forEach { option ->
                DropdownMenuItem(text = {
                    Text(text = option)
                }, onClick = {
                    onOptionSelected(option)
                    selectedOption.value = option
                    expanded.value = false
                }
                )
            }
        }

    }
}