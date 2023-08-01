package com.lgn.presentation.navigations.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lgn.presentation.Screen
import com.lgn.presentation.dashboard.metrics.MetricScreen
import com.lgn.presentation.dashboard.metrics.allmetrics.AllMetricsScreen
import com.lgn.presentation.dashboard.metrics.studentmetricsdetail.StudentMetricsDetailScreen
import com.lgn.presentation.dashboard.myteam.MyTeamScreen
import com.lgn.presentation.navigations.Graph
import com.lgn.presentation.splash.SplashScreen
import com.lgn.presentation.dashboard.myprofile.ProfileScreen
import com.lgn.presentation.dashboard.myteam.studentprofile.StudentProfileScreen
import com.lgn.presentation.dashboard.myteam.studentprofilemetrics.StudentProfileMetricsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun DashNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        route = Graph.DASHBOARD,
        startDestination = Screen.HomeScreen.route
    ) {

        composable(route = Screen.HomeScreen.route) {
            MyTeamScreen(navController = navController)
        }
        composable(route = Screen.StudentProfileScreen.route) {
            StudentProfileScreen(navController = navController)
        }
        composable(route = Screen.StudentProfileMetricsScreen.route) {
            StudentProfileMetricsScreen(navController = navController)
        }
        composable(route = Screen.MetricsScreen.route) {
            MetricScreen(navController = navController)
        }
        composable(route = Graph.ROOT) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        detailsNavGraph(navController = navController)
        bookingsNavGraph(navController = navController)
        metricsNavGraph(navController = navController)
        eventSelectionNavGraph(navController = navController)
        authNavGraph(navController = navController)
    }
}

@OptIn(InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = Screen.EventDetailScreen.route
    ) {
        composable(route = Screen.EventDetailScreen.route) {
            //EventDetailScreen(navController = navController)
        }
        composable(route = Screen.EditVenueScreen.route) {
            //EditVenueScreen(navController = navController)
        }
    }
}


@OptIn(InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.bookingsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AllEvents,
        startDestination = Screen.AllEventsScreen.route
    ) {
        composable(route = Screen.AllEventsScreen.route) {
            //AllEventsScreen(navController = navController)
        }
        composable(route = Screen.EventSummaryScreen.route) {
            //EventSummaryScreen(navController = navController)
        }
    }
}

@OptIn(InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.metricsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.TABLES,
        startDestination = Screen.AllStudentMetricScreen.route
    ) {
        composable(route = Screen.AllStudentMetricScreen.route) {
            AllMetricsScreen(navController = navController)
        }
        composable(route = Screen.StudentMetricsDetail.route) {
            StudentMetricsDetailScreen(navController = navController)
        }
    }
}




@OptIn(InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.eventSelectionNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.EVENTSELECTION,
        startDestination = Screen.EventSelectionScreen.route
    ) {
        composable(route = Screen.EventSelectionScreen.route) {
            //EventSelectionScreen(navController = navController)
        }
        composable(
            route = Screen.ScannerScreen.route,
        ) {
            //EventScannerScreen(navController = navController)
        }
    }
}
