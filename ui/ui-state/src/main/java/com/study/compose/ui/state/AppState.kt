package com.study.compose.ui.state

import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterialApi::class)
class AppState(
    val scaffoldState: BackdropScaffoldState,
    val navController: NavHostController,
) {
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberAppState(
    scaffoldState: BackdropScaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed),
    navController: NavHostController = rememberNavController(),
) = remember(scaffoldState, navController) {
    AppState(
        scaffoldState = scaffoldState,
        navController = navController,
    )
}


