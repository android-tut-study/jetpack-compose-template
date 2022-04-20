package com.study.compose.shrine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.study.compose.ui.home.HomeScreen
import com.study.compose.ui.landing.LandingScreen

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object Home : Screen("home")
}


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) {
            LandingScreen {
                navController.navigate(Screen.Home.route)
            }
        }
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}

