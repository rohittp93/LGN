package com.lgn.presentation.dashboard.metrics.allmetrics

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.lgn.presentation.Screen
import com.lgn.presentation.ui.theme.*
import com.lgn.presentation.ui.utils.CustomProgressBar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun AllMetricsScreen(
    viewModel: AllMetricsViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    var monthYear: String by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = context) {
        monthYear =
            navController.previousBackStackEntry?.savedStateHandle?.get<String>("monthYear")
                ?: ""

        if (monthYear.isNotEmpty()) {
            Log.d("monthYear ", "received on the other side $monthYear")
            viewModel.fetchStudentMetrics(context, monthYear)
        }

    }

    Column(
        verticalArrangement = Arrangement.Top
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
                            .width(40.dp)
                            .height(40.dp)
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }

                if (monthYear.isNotEmpty()) {
                    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(monthYear)
                    val dateFormated =
                        SimpleDateFormat("MMM yyyy").format(date)

                    Text(
                        text = dateFormated,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 24.sp,
                        color = textColorGray
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGray),
            contentAlignment = Alignment.Center
        ) {
            when (val usersResponse = viewModel.usersState.value) {
                is Response.Loading -> CustomProgressBar()
                is Response.Success ->
                    if (usersResponse.data.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopStart)
                        ) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                            ) {
                                items(items = usersResponse.data) { user ->
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .background(color = Color.White)
                                            .clickable {
                                                /* navController.currentBackStackEntry?.savedStateHandle?.set(
                                                        "monthYear", dateFormated
                                                    )
                                                    navController.navigate(Screen.AllStudentMetricScreen.route)*/
                                            }
                                            .fillMaxWidth()
                                            .padding(20.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.people),
                                            contentDescription = null,
                                            colorFilter = ColorFilter.tint(
                                                Color.White
                                            ),
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .background(color = green)
                                                .height(50.dp)
                                                .width(50.dp)
                                                .padding(10.dp)
                                        )

                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Column(
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.Start,
                                                modifier = Modifier.padding(start = 16.dp)
                                            ) {
                                                Text(
                                                    text = "${user.userName}",
                                                    modifier = Modifier.padding(start = 16.dp),
                                                    color = textColorLightGray,
                                                    fontSize = 16.sp
                                                )
                                                Text(
                                                    text = "${user.role}",
                                                    modifier = Modifier.padding(start = 16.dp),
                                                    color = textColorLightGray,
                                                    fontSize = 14.sp
                                                )
                                            }

                                            Text(
                                                text = "VIEW",
                                                modifier = Modifier
                                                    .padding(start = 16.dp)
                                                    .background(green)
                                                    .padding(
                                                        start = 16.dp,
                                                        top = 8.dp,
                                                        bottom = 8.dp,
                                                        end = 16.dp
                                                    ),
                                                color = Color.White,
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "No users found",
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .align(Alignment.Center),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 4.5.sp
                            ),
                            fontSize = 14.sp,
                            color = hintColorGray
                        )

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
