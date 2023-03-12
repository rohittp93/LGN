package com.lgn.presentation.dashboard.metrics

import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.lgn.presentation.ui.theme.*
import com.lgn.presentation.ui.utils.DatePickerview
import com.lgn.presentation.ui.utils.YearPickerDialog
import com.lgn.presentation.ui.utils.convertDateToString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun MetricScreen(viewModel: MetricsViewModel = hiltViewModel(), navController: NavController) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    var showCustomDialog by remember {
        mutableStateOf(false)
    }

    var yearPicked: Int by rememberSaveable {
        mutableStateOf(-1)
    }

    if (showCustomDialog) {
        YearPickerDialog({
            showCustomDialog = !showCustomDialog
        }, onYearSelected = {
            yearPicked = it
        })
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
                    label = if (yearPicked == -1) "Select Year" else yearPicked.toString(),
                    onSelectYearClicked = {
                        showCustomDialog = !showCustomDialog
                    })
            }
        }
    }
}
