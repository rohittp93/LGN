package com.lgn.presentation.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lgn.presentation.ui.theme.green
import com.lgn.presentation.ui.theme.textColorGray
import com.lgn.presentation.ui.theme.textColorLightGray


@Composable
fun YearPickerDialog(onDismiss: () -> Unit, onYearSelected: (Int) -> Unit) {
    var yearState by remember { mutableStateOf(2023) }

    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 8.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(start = 24.dp, end = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Year",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                    ),
                    modifier = Modifier.padding(bottom = 16.dp, top = 8.dp),
                    fontSize = 24.sp,
                    color = textColorLightGray
                )

                NumberPicker(
                    value = yearState,
                    range = 1970..2023,
                    onValueChange = {
                        yearState = it
                    },
                    textStyle = TextStyle(
                        color = textColorLightGray,
                        fontSize = 24.sp
                    )
                )

                Row(Modifier.padding(top = 10.dp)) {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = textColorLightGray),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Cancel", color = Color.White)
                    }

                    Button(
                        onClick = {
                            onYearSelected(yearState)
                            onDismiss()
                                  },
                        colors = ButtonDefaults.buttonColors(backgroundColor = green),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Select", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    label: (Int) -> String = {
        it.toString()
    },
    value: Int,
    onValueChange: (Int) -> Unit,
    dividersColor: Color = textColorLightGray,
    range: Iterable<Int>,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    ListItemPicker(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        dividersColor = dividersColor,
        list = range.toList(),
        textStyle = textStyle
    )
}