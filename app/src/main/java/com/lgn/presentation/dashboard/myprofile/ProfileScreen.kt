package com.lgn.presentation.dashboard.myprofile

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
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
            val context = LocalContext.current
            val packageManager: PackageManager = context.packageManager
            val intent: Intent = packageManager.getLaunchIntentForPackage(context.packageName)!!
            val componentName: ComponentName = intent.component!!
            val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)
            context.startActivity(restartIntent)
            Runtime.getRuntime().exit(0)

            LaunchedEffect(key1 = context) {
                //navController.popBackStack()


            /*navController.navigate(Graph.AUTHENTICATION){
                    popUpTo(Graph.AUTHENTICATION){
                        saveState = false
                        inclusive = false
                    }
                    restoreState = false
                    launchSingleTop = true
                }*/
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {


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
                            text = viewModel.userProfile.value.userName ?: "-",
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
                                text = viewModel.userProfile.value.userPhone ?: "-",
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


                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                            ) {
                                Text(
                                    text = "D.O.B",
                                    modifier = Modifier.padding(top = 16.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Light,
                                    ),
                                    fontSize = 14.sp,
                                    color = textColorLightGray
                                )
                                Text(
                                    text = viewModel.userProfile.value.user_dob ?: "-",
                                    modifier = Modifier.padding(top = 4.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                    ),
                                    fontSize = 16.sp,
                                    color = textColorGray
                                )
                            }

                            /*Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
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
                                    text = viewModel.userProfile.value.userAadhar ?: "-",
                                    modifier = Modifier.padding(top = 4.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                    ),
                                    fontSize = 16.sp,
                                    color = textColorGray
                                )
                            }*/
                        }
                    }
                }


                Box(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(color = Color.White)
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Residential Address",
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                            ),
                            fontSize = 16.sp,
                            color = textColorGray
                        )


                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                            ) {
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
                                    text = viewModel.userProfile.value.userAddress ?: "-",
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
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
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
                                    text = viewModel.userProfile.value.userBlock ?: "-",
                                    modifier = Modifier.padding(top = 4.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                    ),
                                    fontSize = 16.sp,
                                    color = textColorGray
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                            ) {
                                Text(
                                    text = "City/Town/Village",
                                    modifier = Modifier.padding(top = 16.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Light,
                                    ),
                                    fontSize = 14.sp,
                                    color = textColorLightGray
                                )
                                Text(
                                    text = viewModel.userProfile.value.user_city ?: "-",
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
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
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
                                    text = viewModel.userProfile.value.userDistrict ?: "-",
                                    modifier = Modifier.padding(top = 4.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                    ),
                                    fontSize = 16.sp,
                                    color = textColorGray
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
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
                                    text = viewModel.userProfile.value.userPin ?: "-",
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
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
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
                                    text = viewModel.userProfile.value.userState ?: "-",
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



                Box(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(color = Color.White)
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Training Location",
                            style = TextStyle(
                                fontWeight = FontWeight.Black,
                            ),
                            fontSize = 16.sp,
                            color = textColorGray
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                            ) {
                                Text(
                                    text = "City/Town/Village",
                                    modifier = Modifier.padding(top = 16.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Light,
                                    ),
                                    fontSize = 14.sp,
                                    color = textColorLightGray
                                )
                                Text(
                                    text = viewModel.userProfile.value.training_city ?: "-",
                                    modifier = Modifier.padding(top = 4.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                    ),
                                    fontSize = 16.sp,
                                    color = textColorGray
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
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
                                    text = viewModel.userProfile.value.training_district ?: "-",
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
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
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
                                    text = viewModel.userProfile.value.training_pin ?: "-",
                                    modifier = Modifier.padding(top = 4.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                    ),
                                    fontSize = 16.sp,
                                    color = textColorGray
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
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
                                    text = viewModel.userProfile.value.training_state ?: "-",
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
                    .padding(start = 20.dp, top = 40.dp, end = 20.dp, bottom = 40.dp)
                    //.align(Alignment.BottomCenter)
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
}
