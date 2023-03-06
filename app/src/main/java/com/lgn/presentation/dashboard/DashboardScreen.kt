package com.lgn.presentation.dashboard

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lgn.presentation.ui.BottomNavigationBar
import com.lgn.presentation.navigations.graphs.DashNavGraph

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel(), navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        DashNavGraph(navController = navController, it)
    }
}
