package com.lgn.presentation.dashboard.metrics.studentmetricsdetail

import android.util.Log
import android.util.Patterns
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.lgn.R
import com.lgn.domain.model.Response
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.domain.model.UpdateStudentResponse
import com.lgn.domain.model.Users
import com.lgn.presentation.ui.theme.green
import com.lgn.presentation.ui.theme.hintColorGray
import com.lgn.presentation.ui.theme.textColorGray
import com.lgn.presentation.ui.utils.CustomProgressBar
import com.lgn.presentation.ui.utils.MonthPicker
import com.lgn.presentation.ui.utils.convertToMonthAndYear
import com.lgn.presentation.ui.utils.showToast
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UpdateMetricsBottomSheet(
    viewModel: UpdateMetricsViewModel = hiltViewModel(),
    onCloseClicked: (Boolean) -> Unit,
    user: Users? = null,
    addMetric: Boolean = true,
    userResponseData: StudentMerticsResponse? = StudentMerticsResponse()
) {

    Log.d("RTAG", "User object updated " + user?.userFirstname + " UserID: ${user?.userId}")
    val context = LocalContext.current
    val state = viewModel.state

    var visible by remember {
        mutableStateOf(false)
    }

    var canClose by remember {
        mutableStateOf(false)
    }
    val configuration = LocalConfiguration.current

    var date by remember {
        mutableStateOf("")
    }

    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    val year = Calendar.getInstance().get(Calendar.YEAR)

    userResponseData?.let {
        if (!viewModel.initialLaunchCompleted && it.userId != null)
            viewModel.updateStudentMetrics(it)
    }

    user?.monthyear?.let {
        date = convertToMonthAndYear(it)
    }
    viewModel.updateAddMetricsValues(user)

    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
            .background(color = Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Add Metrircs",
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
                        if (canClose) {
                            onCloseClicked(false)
                            viewModel.resetStudentMetrics()
                        } else {
                            showToast(context, "Please wait")
                        }
                    }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        MonthPicker(
            visible = visible,
            currentMonth = currentMonth,
            currentYear = year,
            confirmButtonCLicked = { month_, year_ ->
                date = convertToMonthAndYear(month_, year_)
                visible = false
            },
            cancelClicked = {
                visible = false
            }
        )


        Button(
            border = BorderStroke(0.8.dp, textColorGray),
            shape = RoundedCornerShape(8.dp),
            elevation = null,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            onClick = {
                //visible = true
            }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (date.isEmpty()) "Select Date" else date,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                    ),
                    fontSize = 16.sp,
                    color = textColorGray
                )
                Image(
                    painterResource(id = R.drawable.calendar),
                    contentDescription = "Calendar",
                    modifier = Modifier
                        .height(18.dp)
                        .width(18.dp)
                )
            }
        }




        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OutlinedTextField(
                value = if (state.ev == -1) "" else state.ev.toString(),
                label = {
                    Text(
                        text = "EV",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                modifier = Modifier.weight(1F),
                onValueChange = { newText ->
                    if (newText.isDigitsOnly())
                        viewModel.valueChanged("ev", newText)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = if (state.de == -1) "" else state.de.toString(),
                modifier = Modifier.weight(1F),
                label = {
                    Text(
                        text = "DE",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                onValueChange = { newText ->
                    if (newText.isDigitsOnly())
                        viewModel.valueChanged("de", newText)

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OutlinedTextField(
                value = if (state.jb == -1) "" else state.jb.toString(),
                label = {
                    Text(
                        text = "JB",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                modifier = Modifier.weight(1F),
                onValueChange = { newText ->
                    if (newText.isDigitsOnly())
                        viewModel.valueChanged("jb", newText)

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = if (state.aa == -1) "" else state.aa.toString(),
                modifier = Modifier.weight(1F),
                label = {
                    Text(
                        text = "AA",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                onValueChange = { newText ->
                    if (newText.isDigitsOnly())
                        viewModel.valueChanged("aa", newText)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OutlinedTextField(
                value = if (state.p == -1) "" else state.p.toString(),
                label = {
                    Text(
                        text = "P",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                modifier = Modifier.weight(1F),
                onValueChange = { newText ->
                    if (newText.isDigitsOnly())
                        viewModel.valueChanged("p", newText)

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = if (state.e == -1) "" else state.e.toString(),
                modifier = Modifier.weight(1F),
                label = {
                    Text(
                        text = "E",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                onValueChange = { newText ->
                    if (newText.isDigitsOnly())
                        viewModel.valueChanged("e", newText)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OutlinedTextField(
                value = if (state.a == -1) "" else state.a.toString(),
                label = {
                    Text(
                        text = "A",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                modifier = Modifier.weight(1F),
                onValueChange = { newText ->
                    if (newText.isDigitsOnly())
                        viewModel.valueChanged("a", newText)

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = if (state.c == -1) "" else state.c.toString(),
                modifier = Modifier.weight(1F),
                label = {
                    Text(
                        text = "C",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                onValueChange = { newText ->
                    if (newText.isDigitsOnly())
                        viewModel.valueChanged("c", newText)

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }

        Row(
            modifier = Modifier
                .width(((configuration.screenWidthDp / 2) - 24 - 12).dp)
                .padding(top = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OutlinedTextField(
                value = if (state.ed == -1) "" else state.ed.toString(),
                label = {
                    Text(
                        text = "ED",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                modifier = Modifier.weight(1F),
                onValueChange = { newText ->
                    if (newText.isDigitsOnly())
                        viewModel.valueChanged("ed", newText)

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        when (viewModel.metricsUpdateState.value) {
            is Response.Loading -> {
                canClose = false
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    CustomProgressBar()
                }
            }
            is Response.Success -> {
                canClose = true
                LaunchedEffect(key1 = context) {
                    onCloseClicked(true)
                    viewModel.resetStudentMetrics()
                }
            }
            is Response.Error -> {
                canClose = true
            }
            Response.Idle -> {
                canClose = true
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {

                var errorShown = false
                if (date.isEmpty()) {
                    showToast(context, "Please add date")
                    errorShown = true
                }

                if (state.ev.toString().isEmpty()
                    || state.de.toString().isEmpty()
                    || state.jb.toString().isEmpty()
                    || state.aa.toString().isEmpty()
                    || state.p.toString().isEmpty()
                    || state.e.toString().isEmpty()
                    || state.a.toString().isEmpty()
                    || state.c.toString().isEmpty()
                    || state.ed.toString().isEmpty()
                ) {
                    showToast(context, "Please enter all required fields")
                    errorShown = true
                }

                if (!errorShown) {
                    if (addMetric) {
                        viewModel.updateStudentMetrics(
                            context,
                            state.id ?: "",
                            StudentMerticsResponse(
                                userId = state.userId,
                                monthyear = state.monthyear,
                                ev = state.ev,
                                de = state.de,
                                jb = state.jb,
                                aa = state.aa,
                                p = state.p,
                                e = state.e,
                                a = state.a,
                                c = state.c,
                                ed = state.ed,
                                isDeleted = 0
                            )
                        )
                    } else {
                        viewModel.updateStudentMetrics(
                            context,
                            state.id ?: "",
                            StudentMerticsResponse(
                                id = state.userId,
                                userId = state.userId,
                                monthyear = state.monthyear,
                                ev = state.ev,
                                de = state.de,
                                jb = state.jb,
                                aa = state.aa,
                                p = state.p,
                                e = state.e,
                                a = state.a,
                                c = state.c,
                                ed = state.ed,
                                isDeleted = state.isDeleted,
                            )
                        )
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = green)
        )
        {
            Text(text = "SAVE", color = Color.White)
        }



        Spacer(modifier = Modifier.height(32.dp))
    }
}