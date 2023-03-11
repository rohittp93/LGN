package com.lgn.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lgn.presentation.ui.theme.*
import com.lgn.presentation.ui.utils.Constants

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination =
        Constants.BottomNavItems.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = white,
            modifier = Modifier.height(65.dp)
        ) {
            Constants.BottomNavItems.forEach { navItem ->
                val isSelected = currentDestination?.route == navItem.route
                BottomNavigationItem(
                    selected = isSelected,
                    modifier = Modifier.height(65.dp),
                    onClick = {
                        currentDestination?.let {
                            if (it.route != navItem.route) {
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        }
                    },
                    icon = {
                        Image(
                            painterResource(navItem.drawable),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(
                                if(isSelected) green else borderColorGray
                            ),
                            modifier = Modifier.height(30.dp).width(30.dp)
                        )
                    },
                    label = {
                        Text(text = navItem.label, color =  if(isSelected) green else borderColorGray)
                    },
                    alwaysShowLabel = false
                )

                /*when (role) {
                    "BOUNCER" -> {
                        if (navItem.label != "Venue") {
                            BottomNavigationItem(
                                selected = currentDestination?.route == navItem.route,
                                onClick = {
                                    currentDestination?.let {
                                        if (it.route != navItem.route) {
                                            navController.navigate(navItem.route) {
                                                popUpTo(navController.graph.findStartDestination().id)
                                                launchSingleTop = true
                                            }
                                        }
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = navItem.icon,
                                        contentDescription = navItem.label,
                                        tint = Color.White
                                    )
                                },
                                label = {
                                    Text(text = navItem.label, color = Color.White)
                                },
                                alwaysShowLabel = false
                            )
                        }
                    }
                    else -> {
                        BottomNavigationItem(
                            selected = currentDestination?.route == navItem.route,
                            onClick = {
                                currentDestination?.let {
                                    if (it.route != navItem.route) {
                                        navController.navigate(navItem.route) {
                                            popUpTo(navController.graph.findStartDestination().id)
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = navItem.label,
                                    tint = Color.White
                                )
                            },
                            label = {
                                Text(text = navItem.label, color = Color.White)
                            },
                            alwaysShowLabel = false
                        )
                    }
                }*/
            }
        }
    }
}