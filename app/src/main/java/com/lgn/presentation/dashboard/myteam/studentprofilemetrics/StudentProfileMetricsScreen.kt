package com.lgn.presentation.dashboard.myteam.studentprofilemetrics

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.lgn.domain.model.StudentData
import com.lgn.domain.model.StudentMerticsResponse
import com.lgn.domain.model.Users
import com.lgn.presentation.ui.theme.*
import com.lgn.presentation.ui.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun StudentProfileMetricsScreen(
    viewModel: StudentProfileMetricsViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    var visible by remember {
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
    var date by remember {
        mutableStateOf("")
    }

    val year = Calendar.getInstance().get(Calendar.YEAR)


    BackHandler(sheetState.isVisible) {
        //coroutineScope.launch { sheetState.hide() }
    }

    var user: StudentData by rememberSaveable {
        mutableStateOf(StudentData())
    }

    LaunchedEffect(key1 = context) {
        user =
            navController.previousBackStackEntry?.savedStateHandle?.get<StudentData>("studentData")
                ?: StudentData()

        if (user.id?.isNotEmpty() == true) {
            viewModel.fetchStudentProfileMetrics(context, user.id ?: "", year.toString())
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
                    text = user?.userName ?: "",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 24.sp,
                    color = textColorGray
                )
            }
        }

        /*Button(
            border = BorderStroke(0.8.dp, textColorGray),
            shape = RoundedCornerShape(8.dp),
            elevation = null,
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 24.dp)
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
        }*/


        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundGray)
        ) {
            when (val usersResponse = viewModel.usersState.value) {
                is Response.Loading -> CustomProgressBar()
                is Response.Success -> {
                    val metricsList = usersResponse.data.metrics
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        items(items = metricsList) { item ->
                            Column(
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Metrics: " + if (item.monthyear != null) convertToMonthAndYear(
                                        item.monthyear ?: ""
                                    ) else "",
                                    modifier = Modifier.padding(start = 16.dp, bottom = 10.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                    ),
                                    color = Color.Black,
                                    fontSize = 20.sp
                                )
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
                                            text = item.ev.toString(),
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
                                            text = item.de.toString(),
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
                                            text = item.jb.toString(),
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
                                            text = item.aa.toString(),
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
                                            text = item.p.toString(),
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
                                            text = item.e.toString(),
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
                                            text = item.a.toString(),
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
                                            text = item.c.toString(),
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
                                            text = item.ed.toString(),
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
                            }
                        }
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

