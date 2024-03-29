package com.lgn.presentation.dashboard.metrics.allmetrics

import android.util.Log
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lgn.R
import com.lgn.domain.model.Response
import com.lgn.domain.model.Users
import com.lgn.presentation.Screen
import com.lgn.presentation.dashboard.metrics.studentmetricsdetail.StudentMetricsDetailViewModel
import com.lgn.presentation.dashboard.metrics.studentmetricsdetail.UpdateMetricsBottomSheet
import com.lgn.presentation.ui.theme.*
import com.lgn.presentation.ui.utils.CustomProgressBar
import com.lgn.presentation.ui.utils.MultipleEventsCutter
import com.lgn.presentation.ui.utils.get
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
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
    var canClose by remember {
        mutableStateOf(false)
    }

    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

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

    val secondScreenResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<Boolean>("needsRefresh")?.observeAsState()

    secondScreenResult?.value?.let {
        if (it) {
            viewModel.fetchStudentMetrics(context, monthYear)
            viewModel.checkRefreshState.value = false
        }

        //removing used value
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.remove<Boolean>("needsRefresh")
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

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 8.dp,
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
        sheetContent = {
            UpdateMetricsBottomSheet(
                user = viewModel.selectedUserState.value,
                addMetric = true,
                onCloseClicked = { showuldRefresh ->
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                    }

                    if (showuldRefresh) {
                        viewModel.fetchStudentMetrics(context, monthYear)
                    }
                })
        },
        modifier = Modifier
            .fillMaxSize()
    ) {

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
                                .width(45.dp)
                                .height(45.dp)
                                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                                .clickable {
                                    multipleEventsCutter.processEvent {
                                        navController.popBackStack()
                                    }
                                }
                        )
                    }

                    if (monthYear.isNotEmpty()) {
                        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(monthYear)
                        Log.d("monthYear ", "$monthYear")
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
                                        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                                            val (image, name, button) = createRefs()
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
                                                    .constrainAs(image) {
                                                        start.linkTo(parent.start, margin = 20.dp)
                                                        top.linkTo(parent.top, margin = 10.dp)
                                                        bottom.linkTo(parent.bottom, margin = 10.dp)
                                                    }
                                            )

                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .constrainAs(name) {
                                                        start.linkTo(image.end, margin = 16.dp)
                                                        top.linkTo(parent.top, margin = 10.dp)
                                                        end.linkTo(button.start, margin = 10.dp)
                                                        bottom.linkTo(parent.bottom, margin = 10.dp)
                                                        width = Dimension.fillToConstraints
                                                    }
                                            ) {
                                                Column(
                                                    verticalArrangement = Arrangement.Center,
                                                    horizontalAlignment = Alignment.Start,
                                                ) {
                                                    Text(
                                                        text = "${user.userFirstname ?: ""} ${user.userLastname ?: ""}",
                                                        modifier = Modifier.padding(start = 16.dp),
                                                        maxLines = 2,
                                                        overflow = TextOverflow.Ellipsis,
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
                                            }

                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier
                                                    .width(80.dp)
                                                    .height(35.dp)
                                                    .background(if (user.id == null || user.id?.isEmpty() == true) orange else green)
                                                    .clickable {
                                                        multipleEventsCutter.processEvent {
                                                            if ((user.id == null || user.id?.isEmpty() == true)) {
                                                                viewModel.setSelectedUser(user)
                                                                coroutineScope.launch {
                                                                    if (sheetState.isVisible) sheetState.hide()
                                                                    else sheetState.show()
                                                                }
                                                            } else {
                                                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                                                    "user", user
                                                                )
                                                                navController.navigate(Screen.StudentMetricsDetail.route)
                                                            }
                                                        }
                                                    }
                                                    .constrainAs(button) {
                                                        end.linkTo(parent.end, margin = 10.dp)
                                                        top.linkTo(parent.top, margin = 10.dp)
                                                    }
                                            ) {
                                                Text(
                                                    text = if (user.id == null || user.id?.isEmpty() == true) "ADD" else "VIEW",
                                                    textAlign = TextAlign.Center,
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
}
