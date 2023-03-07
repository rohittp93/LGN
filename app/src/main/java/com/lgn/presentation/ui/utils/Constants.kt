package com.lgn.presentation.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import com.lgn.R
import com.lgn.presentation.Screen

object Constants {

    val BottomNavItems = listOf(
        BottomNavItem(
            label = "My Team",
            drawable = R.drawable.people,
            route = Screen.HomeScreen.route
        ),
        BottomNavItem(
            label = "Venue",
            drawable = R.drawable.people,
            route = Screen.VenueScreen.route
        ),
        BottomNavItem(
            label = "Profile",
            drawable = R.drawable.people,
            route = Screen.ProfileScreen.route
        )
    )
}
