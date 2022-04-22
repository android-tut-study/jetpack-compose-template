package com.study.compose.shrine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.study.compose.ui.detail.Detail
import com.study.compose.ui.home.HomeScreen
import com.study.compose.ui.landing.LandingScreen

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object Products : Screen("products")
}

sealed class ProductScreen(
    private val route: String
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Products : ProductScreen("products")

    object ShowDetail : ProductScreen("show/{id}") {
        fun createRoute(root: Screen, id: Long): String {
            return "${root.route}/show/$id"
        }
    }
}


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) {
            LandingScreen {
                navController.navigate(Screen.Products.route, navOptions {
                    popUpTo(Screen.Landing.route) { inclusive = true }
                })
            }
        }
        addProductTopLevel(navController = navController)
    }
}

fun NavGraphBuilder.addShowProductDetail(
    navController: NavController,
    root: Screen
) {
    composable(route = ProductScreen.ShowDetail.createRoute(root)) {
        Detail()
    }
}

fun NavGraphBuilder.addProducts(navController: NavController, root: Screen) {
    composable(route = ProductScreen.Products.createRoute(root)) {
        HomeScreen(
            onProductSelect = { cart ->
                navController.navigate(ProductScreen.ShowDetail.createRoute(root))
            }
        )
    }
}

fun NavGraphBuilder.addProductTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Products.route,
        startDestination = ProductScreen.Products.createRoute(Screen.Products)
    ) {
        addProducts(navController = navController, root = Screen.Products)
        addShowProductDetail(navController = navController, root = Screen.Products)
    }
}

