package com.lgn.presentation.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sinq.sinqadmin.presentation.ui.theme.primaryColor
import com.lgn.presentation.ui.utils.Constants

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination =
        Constants.BottomNavItems.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = primaryColor
        ) {
            Constants.BottomNavItems.forEach { navItem ->
                /*val dataStore = LocalDataStore(LocalContext.current)
                val role = runBlocking {
                    dataStore.getStringValue(com.sinq.sinqadmin.core.Constants.KEY_ROLE).first()
                }*/

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