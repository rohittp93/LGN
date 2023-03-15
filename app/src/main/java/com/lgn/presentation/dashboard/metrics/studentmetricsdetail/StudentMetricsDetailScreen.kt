package com.lgn.presentation.dashboard.metrics.studentmetricsdetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.domain.model.Users
import com.lgn.presentation.ui.theme.*
import com.lgn.presentation.ui.utils.CustomProgressBar
import com.lgn.presentation.ui.utils.showToast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun StudentMetricsDetailScreen(
    viewModel: StudentMetricsDetailViewModel = hiltViewModel(),
    updateMetricsViewModel: UpdateMetricsViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    var canClose by remember {
        mutableStateOf(false)
    }

    var closeClicked by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = {
            it != ModalBottomSheetValue.HalfExpanded
        },
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        //coroutineScope.launch { sheetState.hide() }
    }

    var user: Users by rememberSaveable {
        mutableStateOf(Users())
    }

    var userResponseData: StudentMerticsResponse by remember {
        mutableStateOf(StudentMerticsResponse())
    }

    LaunchedEffect(key1 = context) {
        user =
            navController.previousBackStackEntry?.savedStateHandle?.get<Users>("user") ?: Users()

        if (user?.id?.isNotEmpty() == true) {
            viewModel.fetchStudentMetrics(context, user?.id ?: "")
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 8.dp,
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),

        sheetContent = {
            UpdateMetricsBottomSheet(
                user = user,
                userResponseData = userResponseData,
                onCloseClicked = {
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                    }
                })
        },
        modifier = Modifier
            .fillMaxSize()
    ) {
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
                                    if (canClose) {
                                        navController.popBackStack()
                                    } else {
                                        showToast(context, "Please wait")
                                    }
                                }
                        )
                    }

                    Text(
                        text = "Student Metrics",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 24.sp,
                        color = textColorGray
                    )
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .background(color = backgroundGray)
                    .padding(top = 24.dp, end = 24.dp, start = 24.dp, bottom = 34.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.account),
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
                Column(
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(
                        text = user?.userName ?: "",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        ),
                        fontSize = 18.sp,
                        color = textColorGray
                    )
                }
            }

            val date = user?.monthyear?.let {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(it)

                val dateFormated =
                    SimpleDateFormat("MMM yyyy").format(sdf)

                Text(
                    text = "Metrics: $dateFormated",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    ),
                    fontSize = 18.sp,
                    color = textColorGray
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundGray),
                contentAlignment = Alignment.Center
            ) {
                when (val usersResponse = viewModel.usersState.value) {
                    is Response.Loading -> CustomProgressBar()
                    is Response.Success -> {
                        Column(
                            verticalArrangement = Arrangement.Top
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .background(color = Color.White)
                                    .padding(top = 4.dp, bottom = 4.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "EV",
                                        modifier = Modifier.padding(start = 16.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = usersResponse.data.ev.toString(),
                                        modifier = Modifier.padding(start = 0.dp),
                                        color = textColorLightGray,
                                        fontSize = 18.sp
                                    )
                                }


                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "DE",
                                        modifier = Modifier.padding(start = 0.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )

                                    Text(
                                        text = usersResponse.data.de.toString(),
                                        modifier = Modifier.padding(end = 16.dp),
                                        color = textColorLightGray,
                                        fontSize = 18.sp
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .background(color = Color.White)
                                    .padding(top = 4.dp, bottom = 4.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "JB",
                                        modifier = Modifier.padding(start = 16.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = usersResponse.data.jb.toString(),
                                        modifier = Modifier.padding(start = 0.dp),
                                        color = textColorLightGray,
                                        fontSize = 18.sp
                                    )
                                }


                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "AA",
                                        modifier = Modifier.padding(start = 0.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )

                                    Text(
                                        text = usersResponse.data.aa.toString(),
                                        modifier = Modifier.padding(end = 16.dp),
                                        color = textColorLightGray,
                                        fontSize = 18.sp
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .background(color = Color.White)
                                    .padding(top = 4.dp, bottom = 4.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "P",
                                        modifier = Modifier.padding(start = 16.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = usersResponse.data.p.toString(),
                                        modifier = Modifier.padding(start = 0.dp),
                                        color = textColorLightGray,
                                        fontSize = 18.sp
                                    )
                                }


                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "E",
                                        modifier = Modifier.padding(start = 0.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )

                                    Text(
                                        text = usersResponse.data.e.toString(),
                                        modifier = Modifier.padding(end = 16.dp),
                                        color = textColorLightGray,
                                        fontSize = 18.sp
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(8.dp))


                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .background(color = Color.White)
                                    .padding(top = 4.dp, bottom = 4.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "A",
                                        modifier = Modifier.padding(start = 16.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = usersResponse.data.a.toString(),
                                        modifier = Modifier.padding(start = 0.dp),
                                        color = textColorLightGray,
                                        fontSize = 18.sp
                                    )
                                }


                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "C",
                                        modifier = Modifier.padding(start = 0.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )

                                    Text(
                                        text = usersResponse.data.c.toString(),
                                        modifier = Modifier.padding(end = 16.dp),
                                        color = textColorLightGray,
                                        fontSize = 18.sp
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(8.dp))


                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .background(color = Color.White)
                                    .padding(top = 4.dp, bottom = 4.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "ED",
                                        modifier = Modifier.padding(start = 16.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = usersResponse.data.ed.toString(),
                                        modifier = Modifier.padding(start = 0.dp),
                                        color = textColorLightGray,
                                        fontSize = 18.sp
                                    )
                                }


                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "",
                                        modifier = Modifier.padding(start = 0.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )

                                    Text(
                                        text = "",
                                        modifier = Modifier.padding(end = 16.dp),
                                        color = textColorLightGray,
                                        fontSize = 18.sp
                                    )
                                }

                            }


                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 100.dp, start = 24.dp, end = 24.dp),
                                onClick = {
                                    //viewModel.onOpenDialogClicked()
                                    coroutineScope.launch {
                                        if (sheetState.isVisible) sheetState.hide()
                                        else sheetState.show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = green)
                            )
                            {
                                Text(text = "EDIT", color = Color.White)
                            }

                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, start = 24.dp, end = 24.dp),
                                onClick = {
                                    updateMetricsViewModel.updateStudentMetrics(
                                        context,
                                        userResponseData.apply {
                                            isDeleted = 1
                                        })
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = green)
                            )
                            {
                                Text(text = "DELETE", color = Color.White)
                            }

                            when (updateMetricsViewModel.metricsUpdateState.value) {
                                is Response.Loading -> {
                                    canClose = false
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp)
                                    ) {
                                        CustomProgressBar()

                                    }
                                }
                                is Response.Success -> {
                                    canClose = true
                                    if (!closeClicked) {
                                        navController.popBackStack()
                                        closeClicked = true
                                    }
                                }
                                is Response.Error -> {
                                    canClose = true
                                }
                                Response.Idle -> {
                                    canClose = true
                                }
                            }
                        }
                        userResponseData = usersResponse.data
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
}

