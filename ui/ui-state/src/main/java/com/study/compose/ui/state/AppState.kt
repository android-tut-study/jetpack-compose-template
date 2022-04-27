package com.study.compose.ui.state

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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


