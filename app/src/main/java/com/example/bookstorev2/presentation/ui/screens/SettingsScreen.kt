package com.example.bookstorev2.presentation.ui.screens

import LangDropDownMenu
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bookstorev2.R
import com.example.bookstorev2.presentation.ui.components.DialogBody
import com.example.bookstorev2.presentation.viewmodels.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    onBackClick: () -> Unit,
    language: String,
    onDeletingSuccess: () -> Unit
) {

    val scope = rememberCoroutineScope()
    var isDarkTheme by remember { mutableStateOf(false) }

    val cacheAlertDialog = remember { mutableStateOf(false) }
    val accountAlertDialog = remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        settingsViewModel.isDarkTheme.collectLatest { value ->
            isDarkTheme = value
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
                LangDropDownMenu(
                    language,
                    onOptionSelected = { selected ->
                        scope.launch {
                            settingsViewModel.setLanguage(selected)
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.app_theme),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { newValue ->
                        isDarkTheme = newValue
                        scope.launch {
                            settingsViewModel.setDark(newValue)
                        }
                    }
                )
            }
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.app_cache),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = { cacheAlertDialog.value = true }

                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = ""
                    )
                }
            }
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.delete_account),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        accountAlertDialog.value = true
                    }

                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = ""
                    )
                }
            }

            when {
                cacheAlertDialog.value ->
                    DialogBody(
                        onDismiss = { cacheAlertDialog.value = false },
                        onConfirm = {
                            cacheAlertDialog.value = false
                            settingsViewModel.onCacheDelete()
                        },
                        dialogTitle = stringResource(R.string.dialog_title_cache),
                        dialogText = stringResource(R.string.dialog_text_cache),
                        icon = Icons.Default.Info,
                        confirmText = stringResource(R.string.button_confirm),
                        dismissText = stringResource(R.string.button_dismiss)
                    )
            }
            when {
                accountAlertDialog.value ->
                    DialogBody(
                        onDismiss = { accountAlertDialog.value = false },
                        onConfirm = {
                            settingsViewModel.onAccountDelete()
                            onDeletingSuccess()
                        },
                        dialogTitle = stringResource(R.string.dialog_title_account_delete),
                        dialogText = stringResource(R.string.dialog_text_account_delete),
                        icon = Icons.Default.Info,
                        confirmText = stringResource(R.string.button_confirm),
                        dismissText = stringResource(R.string.button_dismiss)
                    )
            }
        }


    }


}