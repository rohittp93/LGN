package com.lgn.presentation.dashboard.myteam.studentprofile

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lgn.R
import com.lgn.domain.model.Response
import com.lgn.domain.model.StudentData
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.presentation.Screen
import com.lgn.presentation.ui.theme.*
import com.lgn.presentation.ui.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun StudentProfileScreen(
    viewModel: StudentProfileViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val showDialogState: Boolean by viewModel.showDialog.collectAsState()

    var closeClicked by remember {
        mutableStateOf(false)
    }

    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    var user: StudentData by rememberSaveable {
        mutableStateOf(StudentData())
    }

    var userResponseData: StudentMerticsResponse by remember {
        mutableStateOf(StudentMerticsResponse())
    }

    var showCustomDialog by remember {
        mutableStateOf(false)
    }

    val calendar = GregorianCalendar()
    calendar.time = Date()

    var yearPicked: Int by rememberSaveable {
        mutableStateOf(calendar.get(Calendar.YEAR))
    }

    if (showCustomDialog) {
        YearPickerDialog({
            showCustomDialog = !showCustomDialog
        }, onYearSelected = {
            yearPicked = it
            val date = SimpleDateFormat("MM-yyyy").parse("01-$yearPicked")
            val dateFormated =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date)
            Log.d("dateFormated", dateFormated)

            user.id?.let { userId ->
                viewModel.changeToGraduate(context, userId, dateFormated)
            }
        },
            title = "Select Year Of Completion",
            buttonText = "SAVE"
        )
    }


    SimpleAlertDialog(
        title = if (user.status == 1) "DEACTIVATE" else "ACTIVATE",
        message = "Are you sure you want to " + (if (user.status == 1) "deactivate" else "activate") + " this user?",
        show = showDialogState,
        showDismissButton = true,
        onDismiss = viewModel::onDialogDismiss,
        onConfirm = {
            user.id?.let {
                viewModel.updateStatus(
                    context,
                    it,
                    if (user.status == 1) 0 else 1
                )
            }
            viewModel.onDialogDismiss()
        }
    )
    LaunchedEffect(key1 = context) {
        user =
            navController.previousBackStackEntry?.savedStateHandle?.get<StudentData>("studentData")
                ?: StudentData()

        if (viewModel.loadedFirstTime) {
            user.role?.let { viewModel.updateRole(it) }
            viewModel.loadedFirstTime = false
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = backgroundGray)
            .fillMaxSize()
    ) {
        Surface(elevation = 9.dp, color = Color.White) {
            Box(modifier = Modifier.height(60.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(id = R.drawable.back),
                        contentDescription = "back",
                        colorFilter = ColorFilter.tint(
                            green
                        ),
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp)
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }

                Text(
                    text = "Student Profile",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 24.sp,
                    color = textColorGray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.people),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                Color.White
            ),
            modifier = Modifier
                .clip(CircleShape)
                .background(color = green)
                .height(70.dp)
                .width(70.dp)
                .padding(10.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = user?.userName ?: "",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mail),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    green
                ),
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp)
            )
            Text(
                text = user.userEmail ?: "",
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                ),
                fontSize = 16.sp,
                color = textColorGray
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.phone),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    green
                ),
                modifier = Modifier
                    .height(18.dp)
                    .width(18.dp)
            )
            Text(
                text = user.userPhone ?: "",
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                ),
                fontSize = 16.sp,
                color = textColorGray
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.account),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    green
                ),
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
            )
            Text(
                text = viewModel.rolseState,
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                ),
                fontSize = 16.sp,
                color = textColorGray
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = backgroundGray)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    multipleEventsCutter.processEvent {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "studentData", user
                        )
                        navController.navigate(Screen.StudentProfileMetricsScreen.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = green)
            )
            {
                Text(text = "VIEW METRICS", color = Color.White)
            }
            if (viewModel.rolseState == "Associate") {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        multipleEventsCutter.processEvent {
                            showCustomDialog = !showCustomDialog
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = green)
                )
                {
                    Text(text = "CHANGE TO GRADUATE", color = Color.White)
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    multipleEventsCutter.processEvent {
                        multipleEventsCutter.processEvent {

                            viewModel.onOpenDialogClicked()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = green)
            )
            {
                Text(
                    text = if (user.status == 1) "DEACTIVATE" else "ACTIVATE",
                    color = Color.White
                )
            }
            when (val usersResponse = viewModel.changeToGraduateState.value) {
                is Response.Loading -> CustomProgressBar()
                is Response.Success -> {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("needsRefresh", true)
                    viewModel.updateRole("Graduate")
                }
                is Error -> {
                    usersResponse.message?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(top = 24.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 4.5.sp
                            ),
                            fontSize = 14.sp,
                            color = hintColorGray
                        )
                    }
                }
                else -> {}
            }


            when (val usersResponse = viewModel.studentStatusState.value) {
                is Response.Loading -> CustomProgressBar()
                is Response.Success -> {
                    if (!viewModel.exiting) {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("needsRefresh", true)
                        navController.popBackStack()

                        viewModel.exiting = true
                    }
                }
                is Error -> {
                    usersResponse.message?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(top = 24.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 4.5.sp
                            ),
                            fontSize = 14.sp,
                            color = hintColorGray
                        )
                    }
                }
                else -> {}
            }
        }


    }
}
