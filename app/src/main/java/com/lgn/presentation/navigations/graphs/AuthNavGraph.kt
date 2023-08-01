package com.lgn.presentation.navigations.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.lgn.presentation.Screen
import com.lgn.presentation.login.LoginScreen
import com.lgn.presentation.navigations.Graph
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
    }
}