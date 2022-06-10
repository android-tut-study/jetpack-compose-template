package com.study.compose.shrine.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
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

    object ShowDetail : ProductScreen("show/{productId}") {
        fun createRoute(root: Screen, productId: Long): String {
            return "${root.route}/show/$productId"
        }
    }
}


@Composable
fun AppNavigation(navController: NavHostController, viewModelStoreOwner: ViewModelStoreOwner) {
    NavHost(navController = navController, startDestination = Screen.Landing.route) {

        composable(Screen.Landing.route) {
            LandingScreen {
                navController.navigate(Screen.Products.route, navOptions {
                    popUpTo(Screen.Landing.route) { inclusive = true }
                })
            }
        }
        addProductTopLevel(navController = navController, viewModelStoreOwner = viewModelStoreOwner)
    }
}

fun NavGraphBuilder.addShowProductDetail(
    navController: NavController,
    root: Screen,
) {
    composable(
        route = ProductScreen.ShowDetail.createRoute(root),
        arguments = listOf(navArgument("productId") { type = NavType.LongType; defaultValue = -1 })
    ) { backStackEntry ->
        val productId = backStackEntry.arguments?.getLong("productId", -1) ?: -1
        Detail(
            productId = productId,
            onClosePressed = { navController.popBackStack() },
            onOtherDetailPressed = { otherId ->
                navController.navigate(
                    ProductScreen.ShowDetail.createRoute(root, productId = otherId)
                )
            },
            onFavoritePressed = { /* TODO Process Favorite */ }
        )
    }
}

fun NavGraphBuilder.addProducts(
    navController: NavController,
    root: Screen,
    viewModelStoreOwner: ViewModelStoreOwner
) {
    composable(route = ProductScreen.Products.createRoute(root)) {
        CompositionLocalProvider(
            LocalViewModelStoreOwner provides viewModelStoreOwner
        ) {
            HomeScreen(
                onProductSelect = { productId ->
                    navController.navigate(
                        ProductScreen.ShowDetail.createRoute(
                            root,
                            productId = productId
                        )
                    )
                },
            )
        }

    }
}

fun NavGraphBuilder.addProductTopLevel(
    navController: NavController,
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    navigation(
        route = Screen.Products.route,
        startDestination = ProductScreen.Products.createRoute(Screen.Products)
    ) {
        addProducts(
            navController = navController,
            root = Screen.Products,
            viewModelStoreOwner = viewModelStoreOwner
        )
        addShowProductDetail(
            navController = navController,
            root = Screen.Products,
        )
    }
}

