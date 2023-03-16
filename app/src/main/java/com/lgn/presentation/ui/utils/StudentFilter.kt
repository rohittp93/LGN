package com.lgn.presentation.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lgn.domain.model.StudentFilter
import com.lgn.presentation.ui.theme.green
import com.lgn.presentation.ui.theme.hintColorGray
import com.lgn.presentation.ui.theme.textColorGray
import com.lgn.presentation.ui.theme.textColorLightGray


@Composable
fun StudentFilterDialog(
    onDismiss: () -> Unit,
    onFilterOptionChanged: (StudentFilter) -> Unit,
    userTypeFilter: MutableState<String>,
    statusFilter: MutableState<String>
) {

    val userTypes = listOf("Show All", "Graduate", "Associate")
    val (userSelected, setUserSelected) = remember { mutableStateOf(userTypeFilter.value) }

    val statusTypes = listOf("Both", "Active", "Inactive")
    val (statusSelected, setStatusSelected) = remember { mutableStateOf(statusFilter.value) }


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
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "User Type",
                    style = TextStyle(
                        fontWeight = FontWeight.Light,
                    ),
                    modifier = Modifier.padding(bottom = 6.dp, top = 16.dp),
                    fontSize = 16.sp,
                    color = textColorLightGray
                )

                FilterRadioGroup(
                    mItems = userTypes,
                    userSelected,
                    setUserSelected
                )

                Text(
                    text = "Status",
                    style = TextStyle(
                        fontWeight = FontWeight.Light,
                    ),
                    modifier = Modifier.padding(bottom = 6.dp, top = 16.dp),
                    fontSize = 16.sp,
                    color = textColorLightGray
                )

                FilterRadioGroup(
                    mItems = statusTypes,
                    statusSelected,
                    setStatusSelected
                )

                Row(Modifier.padding(top = 24.dp, bottom = 12.dp)) {
                    Button(
                        onClick = {
                            onFilterOptionChanged(
                                StudentFilter(
                                    userSelected,
                                    statusSelected
                                )
                            )
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
fun FilterRadioGroup(
    mItems: List<String>,
    selected: String,
    setSelected: (selected: String) -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            mItems.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == item,
                        onClick = {
                            setSelected(item)
                        },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = green,
                            unselectedColor = hintColorGray
                        )
                    )
                    Text(
                        text = item,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                        ),
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                }
            }
        }
    }
}