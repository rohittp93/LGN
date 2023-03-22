package com.lgn.presentation.dashboard.myteam.addstudent

import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lgn.R
import com.lgn.domain.model.*
import com.lgn.presentation.ui.theme.green
import com.lgn.presentation.ui.theme.textColorGray
import com.lgn.presentation.ui.utils.CustomProgressBar
import com.lgn.presentation.ui.utils.MonthPicker
import com.lgn.presentation.ui.utils.convertToMonthAndYear
import com.lgn.presentation.ui.utils.showToast
import java.util.*

@Composable
fun AddStudentBottomSheet(
    navController: NavController,
    viewModel: AddStudentViewModel = hiltViewModel(),
    onCloseClicked: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state

    var visible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = green,
                unfocusedBorderColor = green, textColor = textColorGray
            ),
            onValueChange = { newText ->
                viewModel.valueChanged("firstname", newText)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
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
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = green,
                unfocusedBorderColor = green, textColor = textColorGray
            ),
            onValueChange = { newText ->
                viewModel.valueChanged("lastname", newText)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.email,
            label = {
                Text(
                    text = "Email",
                    fontSize = 16.sp,
                    color = textColorGray
                )
            },
            modifier = Modifier.fillMaxWidth(),

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = green,
                unfocusedBorderColor = green, textColor = textColorGray
            ),
            onValueChange = { newText ->
                viewModel.valueChanged("email", newText)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
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

            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = green,
                unfocusedBorderColor = green, textColor = textColorGray
            ),
            onValueChange = { newText ->
                viewModel.valueChanged("phone", newText)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

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
                    state.email = ""
                    state.firstName = ""
                    state.lastName = ""
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
                if (state.email.isEmpty()) {
                    showToast(context, "Please enter email")
                    errorShown = true
                }

                if (state.phone.isEmpty()) {
                    showToast(context, "Please enter phone")
                    errorShown = true
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
                    showToast(context, "Please enter valid email ID")
                    errorShown = true
                }
                if (state.phone.length != 10) {
                    showToast(context, "Please enter correct phone number")
                    errorShown = true
                }

                if (!errorShown) {
                    viewModel.addStudent(
                        context,
                        UpdateStudentResponse(
                            userFirstname = state.firstName,
                            userLastname = state.lastName,
                            userEmail = state.email,
                            userPhone = state.phone,
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
