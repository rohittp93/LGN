package com.lgn.presentation.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lgn.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(viewModel: SplashViewModel = hiltViewModel(), navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(5f).getInterpolation(it)
                })
        )
        //delay(3000L)
        /*if (viewModel.isUserLoggedIn()) {
            navController.navigate(Graph.DASHBOARD) {
                launchSingleTop = true
                popUpTo(Screen.SplashScreen.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(Graph.AUTHENTICATION) {
                launchSingleTop = true
                popUpTo(Screen.SplashScreen.route) {
                    inclusive = true
                }
            }
        }*/
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Image(
            painterResource(id = R.drawable.lgn_logo),
            contentDescription = "App Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}