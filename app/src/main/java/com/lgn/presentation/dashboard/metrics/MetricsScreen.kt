package com.lgn.presentation.dashboard.metrics

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.lgn.presentation.Screen
import com.lgn.presentation.ui.theme.*
import com.lgn.presentation.ui.utils.DatePickerview
import com.lgn.presentation.ui.utils.MultipleEventsCutter
import com.lgn.presentation.ui.utils.YearPickerDialog
import com.lgn.presentation.ui.utils.get
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun MetricScreen(viewModel: MetricsViewModel = hiltViewModel(), navController: NavController) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    val months =
        listOf("Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Nov", "Dec")

    var showCustomDialog by remember {
        mutableStateOf(false)
    }

    val calendar = GregorianCalendar()
    calendar.time = Date()

    var yearPicked: Int by rememberSaveable {
        mutableStateOf(calendar.get(Calendar.YEAR))
    }

    val multipleEventsCutter = remember { MultipleEventsCutter.get() }


    if (showCustomDialog) {
        YearPickerDialog({
            showCustomDialog = !showCustomDialog
        }, onYearSelected = {
            yearPicked = it
        }, "Select Year Of Completion")
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
            .background(backgroundGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
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
                            painterResource(id = R.drawable.lgn_logo),
                            contentDescription = "App Logo",
                            modifier = Modifier.width(130.dp)
                        )
                    }
                    Text(
                        text = "Metrics",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 24.sp,
                        color = textColorGray
                    )
                }
            }
            Box(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                DatePickerview(
                    label = "Select Year",
                    onSelectYearClicked = {
                        showCustomDialog = !showCustomDialog
                    })
            }

            Spacer(modifier = Modifier.padding(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(months) { item ->
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(color = Color.White)
                            .clickable {
                                multipleEventsCutter.processEvent {
                                    val month = String.format("%02d", months.indexOf(item) + 1)
                                    val date =
                                        SimpleDateFormat("MM-yyyy").parse("$month-$yearPicked")
                                    val dateFormated =
                                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date)
                                    Log.d("dateFormated", dateFormated)

                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "monthYear", dateFormated
                                    )
                                    navController.navigate(Screen.AllStudentMetricScreen.route)
                                }
                            }
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.task),
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
                        Text(
                            text = "$item $yearPicked",
                            modifier = Modifier.padding(start = 16.dp),
                            color = textColorLightGray,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}
