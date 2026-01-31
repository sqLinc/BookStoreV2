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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookstorev2.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerBody(

    isAdminState: Boolean,
    onAdminClick: () -> Unit = {},
    onCategoryClick: (String) -> Unit,
    onLogoutClick: () -> Unit,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onNavigateToSettings: () -> Unit = {}
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val categoryList = listOf(
        stringResource(R.string.drawer_body_all),
        stringResource(R.string.drawer_body_favorite),
        stringResource(R.string.drawer_body_read),
        stringResource(R.string.drawer_body_fantasy),
        stringResource(R.string.drawer_body_detective),
        stringResource(R.string.drawer_body_thriller),
        stringResource(R.string.drawer_body_drama),
        stringResource(R.string.drawer_body_biopic),
        stringResource(R.string.drawer_body_adventure)
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Gray)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.drawer_body_categories),
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray))
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(categoryList) { item ->
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onCategoryClick(item)
                        }) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = item,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth()
                        )
                        Spacer(modifier = Modifier
                            .height(12.dp)
                            .size(1.dp))
                        HorizontalDivider()
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray))
                    }
                }
            }
            if (isAdminState) ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(0.5f),
                onClick = {
                    onAdminClick()
                    scope.launch {
                        drawerState.close()
                    }
                },
                icon = { Icon(Icons.Default.Add, stringResource(R.string.add_book_button)) },
                text = { Text(stringResource(R.string.add_book_button)) },
                containerColor = Color.LightGray
            )
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(0.5f),
                onClick = { openAlertDialog.value = true },
                text = { Text(stringResource(R.string.log_out_button)) },
                icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, "") },
                containerColor = Color.LightGray
            )
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(0.5f),
                onClick = {
                    onNavigateToSettings()
                    scope.launch {
                        drawerState.close()
                    }
                },
                text = { Text(stringResource(R.string.settings_button)) },
                icon = { Icon(Icons.Default.Settings, "") },
                containerColor = Color.LightGray
            )

            when {
                openAlertDialog.value ->
                    DialogBody(
                        onDismiss = { openAlertDialog.value = false },
                        onConfirm = {
                            openAlertDialog.value = false
                            onLogoutClick()
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        dialogTitle = stringResource(R.string.logout_dialog_top_text),
                        dialogText = stringResource(R.string.logout_dialog_text),
                        icon = Icons.Default.Info,
                        confirmText = stringResource(R.string.button_confirm),
                        dismissText = stringResource(R.string.button_dismiss)
                    )
            }


        }
    }


}