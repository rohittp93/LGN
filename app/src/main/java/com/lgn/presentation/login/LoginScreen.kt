package com.lgn.presentation.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lgn.R
import com.lgn.domain.model.Response
import com.lgn.presentation.Screen
import com.lgn.presentation.navigations.Graph
import com.lgn.presentation.ui.theme.borderColorGray
import com.lgn.presentation.ui.theme.green
import com.lgn.presentation.ui.theme.primaryColor
import com.lgn.presentation.ui.theme.textColorGray
import com.lgn.presentation.ui.utils.RegistrationFormEvent
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSButtonState
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSButtonType
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSJetPackComposeProgressButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    val imageRes = painterResource(id = R.drawable.lgn_logo)
    val passwordVisibility = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)
    var submitButtonState by remember { mutableStateOf(SSButtonState.IDLE) }
    val loginState = viewModel.registerState.observeAsState(Response.Idle)
    val TAG = "LoginTag"
    val state = viewModel.state
    val context = LocalContext.current
    val hasHandledNavigation = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        scaffoldState = scaffoldState,
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {

                LaunchedEffect(key1 = context) {
                    viewModel.validationEvents.collect { event ->
                        when (event) {
                            is LoginViewModel.ValidationEvent.Success -> {
                                viewModel.loginAdmin(context, scope)
                            }
                        }
                    }
                }

                when (loginState.value) {
                    is Response.Idle -> {
                        submitButtonState = SSButtonState.IDLE
                    }
                    is Response.Loading -> {
                        submitButtonState = SSButtonState.LOADING
                    }
                    is Response.Success -> {
                       if ((loginState.value as Response.Success).data.message!=null &&
                           (loginState.value as Response.Success).data.message?.isNotEmpty() == true) {
                           submitButtonState = SSButtonState.FAILIURE
                           LaunchedEffect(key1 = context) {
                               showToast(context, "Please verify your email")
                           }
                       } else {
                           submitButtonState = SSButtonState.SUCCESS
                           if(!hasHandledNavigation.value) {
                               navController.navigate(Graph.DASHBOARD) {
                                   launchSingleTop = true
                                   hasHandledNavigation.value= true
                                   popUpTo(Screen.LoginScreen.route) {
                                       inclusive = true
                                   }
                               }
                           }
                       }
                    }
                    is Response.Error -> {
                        submitButtonState = SSButtonState.FAILIURE
                        LaunchedEffect(key1 = context) {
                            showToast(context, (loginState.value as Response.Error).message)
                        }
                        submitButtonState = SSButtonState.IDLE
                        viewModel.setStateIdle()
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White), Arrangement.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            imageRes,
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .height(80.dp)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f)
                            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                            .padding(10.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            OutlinedTextField(
                                value = state.userCode, onValueChange = {
                                    viewModel.onEvent(RegistrationFormEvent.UserCodeChanged(it))
                                },
                                isError = state.userCodeError != null,
                                label = { Text(text = "User Code", color = borderColorGray) },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = primaryColor,
                                    unfocusedBorderColor = borderColorGray,
                                    textColor = textColorGray
                                ),
                                placeholder = { Text(text = "User Code", color = borderColorGray) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth(.8f)
                                    .padding(bottom = 6.dp)
                            )
                            if (state.userCodeError != null) {
                                Text(text = state.userCodeError, color = MaterialTheme.colors.error)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedTextField(
                                value = state.password,
                                onValueChange = {
                                    viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
                                },
                                label = { Text(text = "Password", color = borderColorGray) },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = primaryColor,
                                    unfocusedBorderColor = borderColorGray,
                                    textColor = textColorGray
                                ),
                                isError = state.passwordError != null,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                trailingIcon = {
                                    IconButton(onClick = {
                                        passwordVisibility.value = !passwordVisibility.value
                                    }) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = R.drawable.password_eye),
                                            "Password Icon",
                                            tint = if (passwordVisibility.value) primaryColor else borderColorGray
                                        )
                                    }
                                },
                                placeholder = {
                                    Text(text = "Password", color = borderColorGray)
                                },
                                singleLine = true,
                                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                                modifier = Modifier
                                    .fillMaxWidth(.8f)
                                    .padding(bottom = 6.dp)
                            )
                            if (state.passwordError != null) {
                                Text(text = state.passwordError, color = MaterialTheme.colors.error)
                            }
                            Spacer(modifier = Modifier.padding(26.dp))
                            SSJetPackComposeProgressButton(
                                type = SSButtonType.CIRCLE,
                                width = 300.dp,
                                cornerRadius = 8,
                                height = 50.dp,
                                colors = ButtonDefaults.buttonColors(backgroundColor = green),
                                text = "LOGIN",
                                onClick = {
                                    viewModel.onEvent(RegistrationFormEvent.Submit)
                                    focusManager.clearFocus()
                                },
                                assetColor = Color.White,
                                buttonState = submitButtonState
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    )
}


// Function to generate a Toast
private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}