package com.lgn.presentation.dashboard.myteam

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
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
import com.lgn.domain.model.Response
import com.lgn.presentation.Screen
import com.lgn.presentation.ui.theme.*
import com.lgn.presentation.ui.utils.CustomProgressBar
import com.lgn.presentation.ui.utils.StudentFilterDialog
import com.lgn.presentation.ui.utils.getFilterFromStatus
import com.lgn.presentation.ui.utils.getStatusFromFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun MyTeamScreen(viewModel: MyTeamViewModel = hiltViewModel(), navController: NavController) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val imageRes = painterResource(id = R.drawable.lgn_logo)
    var showCustomDialog by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
            .background(backgroundGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
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
                        Image(
                            painterResource(id = R.drawable.filter),
                            contentDescription = "App Logo",
                            colorFilter = ColorFilter.tint(
                                green
                            ),
                            modifier = Modifier
                                .height(26.dp)
                                .padding(end = 16.dp)
                                .clickable {
                                    showCustomDialog = !showCustomDialog
                                }
                        )
                    }
                    Text(
                        text = "My Team",
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

        if (showCustomDialog) {
            StudentFilterDialog(
                userTypeFilter = viewModel.userTypeFilter,
                statusFilter = viewModel.statusFilter,
                onDismiss = {
                    showCustomDialog = !showCustomDialog
                },
                onFilterOptionChanged = { sf ->
                    viewModel.showFilterList(true)

                    viewModel.userTypeFilter.value = sf.userType ?: "Show All"
                    viewModel.statusFilter.value = sf.statusType ?: "Both"

                    viewModel.updateFilterList(viewModel.teamListState.filter {
                        if(sf.statusType != "Both"){
                            getFilterFromStatus(it.status) == sf.statusType
                        } else {
                            it.status == 1 || it.status == 0
                        } &&
                        if(sf.userType != "Show All") {
                            it.role == sf.userType
                        } else {
                            (it.role == "Associate") || (it.role == "Graduate")
                        }

                         /*&& !sf.userType.equals("Show All")*/
                    })
                }
            )
        }


        when (val teamResponse = viewModel.teamState.value) {
            is Response.Loading -> CustomProgressBar()
            is Response.Success ->
                if (teamResponse.data.associate.isNotEmpty()) {
                    viewModel.updateList(teamResponse.data.associate)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopStart)
                            .padding(top = 70.dp),
                    ) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            items(items = if(viewModel.showFilterList.value)
                                viewModel.filteredTeamListState
                            else viewModel.teamListState
                            ) { user ->
                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .background(color = Color.White)
                                        .clickable {
                                            /*navController.currentBackStackEntry?.savedStateHandle?.set(
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
                                                fontSize = 18.sp
                                            )
                                            Text(
                                                text = "${user.role}",
                                                modifier = Modifier.padding(start = 16.dp),
                                                color = textColorLightGray,
                                                fontSize = 16.sp
                                            )
                                        }

                                        /*Text(
                                            text = if (user.id?.isEmpty() == true) "ADD" else "VIEW",
                                            modifier = Modifier
                                                .padding(start = 16.dp)
                                                .clickable {
                                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                                        "user", user
                                                    )
                                                    navController.navigate(Screen.StudentMetricsDetail.route)
                                                }
                                                .background(if (user.id?.isEmpty() == true) orange else green)
                                                .padding(
                                                    start = 16.dp,
                                                    top = 8.dp,
                                                    bottom = 8.dp,
                                                    end = 16.dp
                                                ),
                                            color = Color.White,
                                            fontSize = 14.sp
                                        )*/

                                        Image(
                                            painter = if (user.status == 1) painterResource(id = R.drawable.tick) else painterResource(
                                                id = R.drawable.cross
                                            ),
                                            contentDescription = null,
                                            colorFilter = ColorFilter.tint(
                                                if (user.status == 1) green else errorRed
                                            ),
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .height(50.dp)
                                                .width(50.dp)
                                                .padding(10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "No upcoming events",
                        modifier = Modifier.padding(top = 24.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 4.5.sp
                        ),
                        fontSize = 14.sp,
                        color = hintColorGray
                    )
                }
            is Response.Error -> {
                Text(
                    text = teamResponse.message,
                    modifier = Modifier.padding(top = 24.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 4.5.sp
                    ),
                    fontSize = 14.sp,
                    color = hintColorGray
                )
            }
            Response.Idle -> {

            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 20.dp, end = 20.dp),
            onClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "event", null
                )
                navController.navigate(Screen.EventDetailScreen.route)
            },
        ) {
            Icon(Icons.Filled.Add, "", tint = Color.White)
        }
    }

}
