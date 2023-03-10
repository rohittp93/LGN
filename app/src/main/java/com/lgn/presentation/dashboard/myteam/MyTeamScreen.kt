package com.lgn.presentation.dashboard.myteam

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
import com.lgn.presentation.Screen
import com.lgn.presentation.ui.theme.*
import com.lgn.presentation.ui.utils.CustomProgressBar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun MyTeamScreen(viewModel: MyTeamViewModel = hiltViewModel(), navController: NavController) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val imageRes = painterResource(id = R.drawable.lgn_logo)

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
                            modifier = Modifier.height(26.dp).padding(end = 16.dp)
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
        when (val teamResponse = viewModel.teamState.value) {
            is Response.Loading -> CustomProgressBar()
            is Response.Success ->
                if (teamResponse.data.associate.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopStart)
                            .padding(top = 78.dp),
                    ) {
                        /*LazyColumn(modifier = Modifier
                            .fillMaxWidth()) {
                            items(items = tablesResponse.data) { event ->
                                EventItem(
                                    event = event,
                                    onEventClicked = {
                                        if ((adminDetails.admin_type ?: "") != Constants.BOUNCER) {
                                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                                "event", event
                                            )
                                            if(event.bookingStartTime.before(Date())) {
                                                navController.navigate(Screen.EventSummaryScreen.route)
                                            } else {
                                                navController.navigate(Screen.EventDetailScreen.route)
                                            }
                                        }
                                    }
                                )
                            }
                        }*/
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
