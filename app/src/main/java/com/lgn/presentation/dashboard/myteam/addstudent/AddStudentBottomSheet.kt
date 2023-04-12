package com.lgn.presentation.dashboard.myteam.addstudent

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lgn.R
import com.lgn.domain.model.*
import com.lgn.presentation.ui.theme.green
import com.lgn.presentation.ui.theme.textColorGray
import com.lgn.presentation.ui.utils.*
import java.util.*

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun AddStudentBottomSheet(
    navController: NavController,
    viewModel: AddStudentViewModel = hiltViewModel(),
    onCloseClicked: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var showCustomDialog by remember {
        mutableStateOf(false)
    }
    val listItems = listOf("Lead Pastor", "Assistant Pastor", "Lay-leader")
    val contextForToast = LocalContext.current.applicationContext

    // state of the menu
    var expanded by remember { mutableStateOf(false) }

    // remember the selected item
    var selectedItem by remember { mutableStateOf(listItems[0]) }

    val calendar = GregorianCalendar()
    calendar.time = Date()

    var yearPicked: String by rememberSaveable {
        mutableStateOf(
            calendar.get(Calendar.YEAR)
                .toString()
        )
    }

    var monthYear: String by rememberSaveable {
        mutableStateOf(
            convertToMonthYear(
                calendar.get(Calendar.YEAR).toString()
            )
        )
    }
    var visible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "ADD ASSOCIATE",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                ),
                fontSize = 16.sp,
                color = textColorGray
            )

            Image(
                painterResource(id = R.drawable.close),
                contentDescription = "Calendar",
                modifier = Modifier
                    .height(15.dp)
                    .width(15.dp)
                    .clickable {
                        state.batch = ""
                        state.firstName = ""
                        state.lastName = ""
                        state.userPosition = ""
                        state.phone = ""
                        onCloseClicked(false)
                    }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = state.firstName,
            label = {
                Text(
                    text = "Associate First Name",
                    fontSize = 16.sp,
                    color = textColorGray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = green,
                unfocusedBorderColor = green, textColor = textColorGray
            ),
            onValueChange = { newText ->
                viewModel.valueChanged("firstname", newText)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.lastName,
            label = {
                Text(
                    text = "Associate Last Name",
                    fontSize = 16.sp,
                    color = textColorGray
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = green,
                unfocusedBorderColor = green, textColor = textColorGray
            ),
            onValueChange = { newText ->
                viewModel.valueChanged("lastname", newText)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.phone,
            label = {
                Text(
                    text = "Phone",
                    fontSize = 16.sp,
                    color = textColorGray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = green,
                unfocusedBorderColor = green, textColor = textColorGray
            ),
            onValueChange = { newText ->
                viewModel.valueChanged("phone", newText)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() })
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (showCustomDialog) {
            YearPickerDialog({
                showCustomDialog = !showCustomDialog
            }, onYearSelected = {
                yearPicked = it.toString()
                monthYear = convertToMonthYear(yearPicked)
                viewModel.valueChanged("batch", monthYear)
            },
                title = "Select Year",
                buttonText = "SELECT"
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,) {
            Text("Batch : ",  style = TextStyle(
                fontWeight = FontWeight.Normal,
            ),
                fontSize = 16.sp,
                color = textColorGray)
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
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
        Spacer(modifier = Modifier.height(16.dp))

        // box
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = it
            }
        ) {
            // text field
            TextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Position in Church") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    textColor = textColorGray,
                    disabledIndicatorColor = textColorGray,
                    unfocusedIndicatorColor = textColorGray,
                    unfocusedLabelColor = textColorGray,
                    disabledLabelColor = textColorGray,
                    trailingIconColor = textColorGray,
                    focusedTrailingIconColor = textColorGray,
                    disabledTrailingIconColor = textColorGray,
                    errorTrailingIconColor = textColorGray,
                ),
                //colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // this is a column scope
                // all the items are added vertically
                listItems.forEach { selectedOption ->
                    // menu item
                    DropdownMenuItem(
                        onClick = {
                            selectedItem = selectedOption
                            viewModel.valueChanged("userPosition", selectedOption)
                            expanded = false
                        },
                        content = {
                            Text(text = selectedOption)
                        }
                    )/* {
                        Text(text = selectedOption)
                    }*/
                }
            }
        }
        Spacer(modifier = Modifier.height(52.dp))

        when (viewModel.studentAddedeState.value) {
            is Response.Loading -> {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                ) {
                    CustomProgressBar()
                }
            }
            is Response.Success -> {
                Log.d("RTAG ", "onCloseClicked called")
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("needsRefresh", true)
                LaunchedEffect(key1 = context) {
                    state.batch = ""
                    state.firstName = ""
                    state.lastName = ""
                    state.userPosition = ""
                    state.phone = ""
                    onCloseClicked(true)
                }
            }
            is Response.Error -> {
            }
            Response.Idle -> {
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                var errorShown = false
                if (state.firstName.isEmpty()) {
                    showToast(context, "Please enter first name")
                    errorShown = true
                }
                if (state.lastName.isEmpty()) {
                    showToast(context, "Please enter last name")
                    errorShown = true
                }
                if (state.batch.equals("Select Year")) {
                    showToast(context, "Please select batch")
                    errorShown = true
                }
                if (state.phone.isEmpty()) {
                    showToast(context, "Please enter phone")
                    errorShown = true
                }
                if (state.phone.length != 10) {
                    showToast(context, "Please enter correct phone number")
                    errorShown = true
                }
                if (state.userPosition.isEmpty()) {
                    showToast(context, "Please enter position in Church")
                    errorShown = true
                }

                if (!errorShown) {
                    focusManager.clearFocus()
                    viewModel.addStudent(
                        context,
                        UpdateStudentResponse(
                            userFirstname = state.firstName,
                            userLastname = state.lastName,
                            userPhone = state.phone,
                            user_position = state.userPosition,
                            batch = state.batch,
                            roleId = "Associate",
                            status = 1,
                        )
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = green)
        )
        {
            Text(text = "SAVE", color = Color.White)
        }



        Spacer(modifier = Modifier.height(80.dp))
    }
}
