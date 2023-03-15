package com.lgn.presentation.dashboard.myteam.addstudent

import android.util.Log
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
    viewModel: AddStudentViewModel = hiltViewModel(),
    onCloseClicked: () -> Unit
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
                text = "ADD STUDENT",
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
                        onCloseClicked()
                    }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = state.name,
            label = {
                Text(
                    text = "Student Name",
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
                viewModel.valueChanged("name", newText)
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
                LaunchedEffect(key1 = context) {
                    onCloseClicked()
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
                viewModel.addStudent(
                    context,
                    UpdateStudentResponse(
                        userFirstname = state.name,
                        userEmail = state.email,
                        userPhone = state.phone
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = green)
        )
        {
            Text(text = "SAVE", color = Color.White)
        }



        Spacer(modifier = Modifier.height(80.dp))
    }
}
