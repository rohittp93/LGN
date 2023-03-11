package com.lgn.presentation.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import com.lgn.R
import com.lgn.presentation.Screen

object Constants {

    val BottomNavItems = listOf(
        BottomNavItem(
            label = "MY TEAM",
            drawable = R.drawable.people,
            route = Screen.HomeScreen.route
        ),
        BottomNavItem(
            label = "METRICS",
            drawable = R.drawable.document,
            route = Screen.MetricsScreen.route
        ),
        BottomNavItem(
            label = "MY PROFILE",
            drawable = R.drawable.account,
            route = Screen.ProfileScreen.route
        )
    )
}
