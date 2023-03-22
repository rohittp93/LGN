package com.lgn.presentation.ui.utils

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun StudentFilterDialog(
    onDismiss: () -> Unit,
    onFilterOptionChanged: (StudentFilter) -> Unit,
    userTypeFilter: MutableState<String>,
    yearFilterSelected: MutableState<String>,
    statusFilter: MutableState<String>
) {

    val calendar = GregorianCalendar()
    calendar.time = Date()

    var yearPicked: String by rememberSaveable {
        mutableStateOf(
            if (yearFilterSelected.value.isEmpty()) calendar.get(Calendar.YEAR)
                .toString() else convertToYear(yearFilterSelected.value)
        )
    }

    var monthYear: String by rememberSaveable {
        mutableStateOf(
            convertToMonthYear(
                calendar.get(Calendar.YEAR).toString()
            )
        )
    }

    var showCustomDialog by remember {
        mutableStateOf(false)
    }
    val userTypes = listOf("Show All", "Graduate", "Associate")
    val (userSelected, setUserSelected) = remember { mutableStateOf(userTypeFilter.value) }

    val statusTypes = listOf("Both", "Active", "Inactive")
    val (statusSelected, setStatusSelected) = remember { mutableStateOf(statusFilter.value) }

    if (showCustomDialog) {
        YearPickerDialog({
            showCustomDialog = !showCustomDialog
        }, onYearSelected = {
            yearPicked = it.toString()
            monthYear = convertToMonthYear(yearPicked)

            /*user.id?.let { userId ->
                viewModel.fetchStudentProfileMetrics(context, userId, dateFormated)
            }*/
        },
            title = "Select Year",
            buttonText = "SELECT"
        )
    }

    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp),
            elevation = 8.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .wrapContentSize()
                    .verticalScroll(rememberScrollState())
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

                if (userSelected.equals("Graduate")) {
                    Text(
                        text = "Batch",
                        style = TextStyle(
                            fontWeight = FontWeight.Light,
                        ),
                        modifier = Modifier.padding(bottom = 6.dp, top = 16.dp),
                        fontSize = 16.sp,
                        color = textColorLightGray
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        DatePickerview(
                            label = if (yearPicked
                                    .isNotEmpty()
                            ) yearPicked else "Select Year",
                            onSelectYearClicked = {
                                showCustomDialog = !showCustomDialog
                            }, onCloseIconClicked = {})
                    }
                }

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
                                    monthYear,
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