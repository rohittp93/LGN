package com.lgn.presentation.ui.utils

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.lgn.presentation.ui.theme.primaryColor
import com.lgn.presentation.ui.theme.textColorGray

@Composable
fun SimpleAlertDialog(
    title: String,
    message: String,
    show: Boolean,
    showDismissButton: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        AlertDialog(
            backgroundColor = Color.White,
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = "OK") }
            },
            dismissButton = if (showDismissButton) {
                {
                    TextButton(onClick = onDismiss)
                    { Text(text = "Cancel") }
                }
            } else null,
            title = { Text(text = title, color = textColorGray) },
            text = { Text(text = message,  color = primaryColor) }
        )
    }
}