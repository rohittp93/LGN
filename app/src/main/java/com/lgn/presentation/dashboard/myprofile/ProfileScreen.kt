package com.lgn.presentation.dashboard.myprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
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
import com.lgn.presentation.Screen
import com.lgn.presentation.navigations.Graph
import com.lgn.presentation.ui.theme.*
import com.lgn.presentation.ui.utils.SimpleAlertDialog

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), navController: NavController) {
    val context = LocalContext.current
    val showDialogState: Boolean by viewModel.showDialog.collectAsState()
    val registerState = viewModel.logoutState.observeAsState(Response.Idle)

    when (registerState.value) {
        is Response.Success -> {
            LaunchedEffect(key1 = context) {
                navController.navigate(Graph.ROOT) {
                    launchSingleTop = true
                    popUpTo(Screen.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        else -> {

        }
    }

    SimpleAlertDialog(
        title = "Logout",
        message = "Are you sure you want to logout",
        show = showDialogState,
        showDismissButton = true,
        onDismiss = viewModel::onDialogDismiss,
        onConfirm = {
            viewModel.logoutUser(context)
        }
    )

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
                    }
                    Text(
                        text = "Profile",
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
                        text = "Trainer 1",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        ),
                        fontSize = 18.sp,
                        color = textColorGray
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 8.dp)
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
                            text = "trainer1@gmail.com",
                            modifier = Modifier.padding(start = 8.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.Light,
                            ),
                            fontSize = 14.sp,
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
                            text = "+919545407890",
                            modifier = Modifier.padding(start = 8.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.Light,
                            ),
                            fontSize = 14.sp,
                            color = textColorGray
                        )
                    }

                }
            }

            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Personal Details",
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                        ),
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                    Text(
                        text = "Address",
                        modifier = Modifier.padding(top = 16.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Light,
                        ),
                        fontSize = 14.sp,
                        color = textColorLightGray
                    )
                    Text(
                        text = "Nequo Porro quisquam est qui dolorem ipsum",
                        modifier = Modifier.padding(top = 4.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                        ),
                        fontSize = 16.sp,
                        color = textColorGray
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                        ) {
                            Text(
                                text = "Block",
                                modifier = Modifier.padding(top = 16.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Light,
                                ),
                                fontSize = 14.sp,
                                color = textColorLightGray
                            )
                            Text(
                                text = "Block Test",
                                modifier = Modifier.padding(top = 4.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                ),
                                fontSize = 16.sp,
                                color = textColorGray
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                        ) {
                            Text(
                                text = "District",
                                modifier = Modifier.padding(top = 16.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Light,
                                ),
                                fontSize = 14.sp,
                                color = textColorLightGray
                            )
                            Text(
                                text = "Test District",
                                modifier = Modifier.padding(top = 4.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                ),
                                fontSize = 16.sp,
                                color = textColorGray
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                        ) {
                            Text(
                                text = "Pincode",
                                modifier = Modifier.padding(top = 16.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Light,
                                ),
                                fontSize = 14.sp,
                                color = textColorLightGray
                            )
                            Text(
                                text = "102103",
                                modifier = Modifier.padding(top = 4.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                ),
                                fontSize = 16.sp,
                                color = textColorGray
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                        ) {
                            Text(
                                text = "State",
                                modifier = Modifier.padding(top = 16.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Light,
                                ),
                                fontSize = 14.sp,
                                color = textColorLightGray
                            )
                            Text(
                                text = "Test State",
                                modifier = Modifier.padding(top = 4.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                ),
                                fontSize = 16.sp,
                                color = textColorGray
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                        ) {
                            Text(
                                text = "Aadhar Card No.",
                                modifier = Modifier.padding(top = 16.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Light,
                                ),
                                fontSize = 14.sp,
                                color = textColorLightGray
                            )
                            Text(
                                text = "123456789",
                                modifier = Modifier.padding(top = 4.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                ),
                                fontSize = 16.sp,
                                color = textColorGray
                            )
                        }
                    }
                }
            }

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                .align(Alignment.BottomCenter)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    viewModel.onOpenDialogClicked()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = green)
            )
            {
                Text(text = "LOGOUT", color = Color.White)
            }
        }

    }
}
