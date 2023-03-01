package com.lgn.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lgn.presentation.Screen
import com.lgn.presentation.navigations.graphs.authNavGraph
import com.lgn.presentation.splash.SplashScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val DASHBOARD = "dashboard_graph"
    const val DETAILS = "details_graph"
    const val AllEvents = "all_events_graph"
    const val TABLES = "tables_graph"
    const val EVENTSELECTION = "eventselection_graph"
    const val SCANNER = "scanner_graph"
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Screen.SplashScreen.route,
    ) {
         composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        authNavGraph(navController = navController)
        /* composable(route = Graph.DASHBOARD) {
            DashboardScreen()
        }*/
    }
}
