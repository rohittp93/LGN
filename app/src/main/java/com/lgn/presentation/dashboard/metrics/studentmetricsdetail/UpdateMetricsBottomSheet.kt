package com.lgn.presentation.dashboard.metrics.studentmetricsdetail

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lgn.R
import com.lgn.domain.model.Response
import com.lgn.domain.model.StudentMerticsResponse
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
    onCloseClicked: () -> Unit,
    user: Users? = null,
    userResponseData: StudentMerticsResponse? = null
) {
    val context = LocalContext.current

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

    var ev_text by remember { mutableStateOf(TextFieldValue("0")) }
    var de_text by remember { mutableStateOf(TextFieldValue("0")) }
    var jb_text by remember { mutableStateOf(TextFieldValue("0")) }
    var aa_text by remember { mutableStateOf(TextFieldValue("0")) }
    var p_text by remember { mutableStateOf(TextFieldValue("0")) }
    var e_text by remember { mutableStateOf(TextFieldValue("0")) }
    var a_text by remember { mutableStateOf(TextFieldValue("0")) }
    var c_text by remember { mutableStateOf(TextFieldValue("0")) }
    var ed_text by remember { mutableStateOf(TextFieldValue("0")) }


    if (userResponseData != null) {
        ev_text = TextFieldValue(userResponseData.ev.toString())
        de_text = TextFieldValue(userResponseData.de.toString())
        jb_text = TextFieldValue(userResponseData.jb.toString())
        aa_text = TextFieldValue(userResponseData.aa.toString())
        p_text = TextFieldValue(userResponseData.p.toString())
        e_text = TextFieldValue(userResponseData.e.toString())
        a_text = TextFieldValue(userResponseData.a.toString())
        c_text = TextFieldValue(userResponseData.c.toString())
        ed_text = TextFieldValue(userResponseData.ed.toString())
    }


    user?.monthyear?.let {
        date = convertToMonthAndYear(it)
    }

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
                            onCloseClicked()
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
                visible = true
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
                value = ev_text,
                label = {
                    Text(
                        text = "EV",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                modifier = Modifier.weight(1F),
                onValueChange = { newText ->
                    ev_text = newText
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = de_text,
                modifier = Modifier.weight(1F),
                label = {
                    Text(
                        text = "DE",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                onValueChange = { newText ->
                    de_text = newText
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
                value = jb_text,
                label = {
                    Text(
                        text = "JB",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                modifier = Modifier.weight(1F),
                onValueChange = { newText ->
                    jb_text = newText
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = aa_text,
                modifier = Modifier.weight(1F),
                label = {
                    Text(
                        text = "AA",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                onValueChange = { newText ->
                    aa_text = newText
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
                value = p_text,
                label = {
                    Text(
                        text = "P",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                modifier = Modifier.weight(1F),
                onValueChange = { newText ->
                    p_text = newText
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = e_text,
                modifier = Modifier.weight(1F),
                label = {
                    Text(
                        text = "E",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                onValueChange = { newText ->
                    e_text = newText
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
                value = a_text,
                label = {
                    Text(
                        text = "A",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                modifier = Modifier.weight(1F),
                onValueChange = { newText ->
                    a_text = newText
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = c_text,
                modifier = Modifier.weight(1F),
                label = {
                    Text(
                        text = "C",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                onValueChange = { newText ->
                    c_text = newText
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
                value = ed_text,
                label = {
                    Text(
                        text = "ED",
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = green,
                    unfocusedBorderColor = green, textColor = textColorGray
                ),
                modifier = Modifier.weight(1F),
                onValueChange = { newText ->
                    ed_text = newText
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        when (viewModel.metricsUpdateState.value) {
            is Response.Loading -> {
                canClose = false
                CustomProgressBar()
            }
            is Response.Success -> {
                canClose = true
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
                viewModel.updateStudentMetrics(
                    context,
                    StudentMerticsResponse(
                        userId = userResponseData?.id,
                        monthyear = userResponseData?.monthyear,
                        ev = userResponseData?.ev,
                        de = userResponseData?.de,
                        jb = userResponseData?.jb,
                        aa = userResponseData?.aa,
                        p = userResponseData?.p,
                        e = userResponseData?.e,
                        a = userResponseData?.a,
                        c = userResponseData?.c,
                        ed = userResponseData?.ed,
                        isDeleted = userResponseData?.isDeleted,

                        )
                )
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = green)
        )
        {
            Text(text = "SAVE", color = Color.White)
        }



        Spacer(modifier = Modifier.height(32.dp))
    }
}
