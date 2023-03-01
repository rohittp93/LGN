package com.lgn.presentation.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SnackbarHost(
    //hostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbar: @Composable (SnackbarData) -> Unit = { snackbarData: SnackbarData ->
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color.White),
            modifier = Modifier
                .padding(16.dp)
                .wrapContentSize()
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "")
                Text(text = snackbarData.message)
            }
        }
    }) {}