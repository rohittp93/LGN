package com.lgn.presentation.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import com.lgn.presentation.Screen
import com.lgn.presentation.ui.utils.BottomNavItem

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Events",
            icon = Icons.Filled.Star,
            route = Screen.HomeScreen.route
        ),
        BottomNavItem(
            label = "Venue",
            icon = Icons.Filled.Place,
            route = Screen.VenueScreen.route
        ),
        BottomNavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = Screen.ProfileScreen.route
        )
    )
}
