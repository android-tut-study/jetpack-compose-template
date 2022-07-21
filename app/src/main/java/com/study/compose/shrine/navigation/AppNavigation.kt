package com.study.compose.shrine.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navOptions
import com.example.ui.qr.Qr
import com.study.compose.ui.detail.Detail
import com.study.compose.ui.home.HomeScreen
import com.study.compose.ui.landing.LandingScreen
import com.study.compose.ui.state.AppStateViewModel

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object Products : Screen("products")
    object Qr : Screen("qr")
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


val uri = "study.compose.shrine"

@Composable
fun AppNavigation(navController: NavHostController, appStateViewModel: AppStateViewModel) {
    NavHost(navController = navController, startDestination = Screen.Landing.route) {

        composable(Screen.Landing.route) {
            LandingScreen {
                navController.navigate(
                    Screen.Products.route,
                    navOptions {
                        popUpTo(Screen.Landing.route) { inclusive = true }
                    }
                )
            }
        }
        addProductTopLevel(navController = navController, appStateViewModel = appStateViewModel)

        composable(Screen.Qr.route) {
            Qr(
                onClosed = { navController.popBackStack() },
                onImageSelectPressed = {
                    // TODO create Image Select screen
                }
            )
        }
    }
}

fun NavGraphBuilder.addShowProductDetail(
    navController: NavController,
    root: Screen,
    appStateViewModel: AppStateViewModel
) {
    composable(
        route = ProductScreen.ShowDetail.createRoute(root),
        arguments = listOf(navArgument("productId") { type = NavType.LongType; defaultValue = -1 }),
        deepLinks = listOf(navDeepLink { uriPattern = "$uri/{productId}" })
    ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getLong("productId", -1) ?: -1
            Detail(
                appViewStateVM = appStateViewModel,
                productId = productId,
                onClosePressed = { navController.popBackStack() },
                onOtherDetailPressed = { otherId ->
                    navController.navigate(
                        ProductScreen.ShowDetail.createRoute(root, productId = otherId)
                    )
                },
            )
    }
}

fun NavGraphBuilder.addProducts(
    navController: NavController,
    root: Screen,
    appStateViewModel: AppStateViewModel
) {
    composable(route = ProductScreen.Products.createRoute(root)) {
            HomeScreen(
                appStateViewModel = appStateViewModel,
                onProductSelect = { productId ->
                    navController.navigate(
                        ProductScreen.ShowDetail.createRoute(
                            root,
                            productId = productId
                        )
                    )
                },
                onQrPressed = {
                    navController.navigate(Screen.Qr.route)
                }
            )
    }
}

fun NavGraphBuilder.addProductTopLevel(
    navController: NavController,
    appStateViewModel: AppStateViewModel,
) {
    navigation(
        route = Screen.Products.route,
        startDestination = ProductScreen.Products.createRoute(Screen.Products)
    ) {
        addProducts(
            navController = navController,
            root = Screen.Products,
            appStateViewModel = appStateViewModel
        )
        addShowProductDetail(
            navController = navController,
            root = Screen.Products,
            appStateViewModel = appStateViewModel
        )
    }
}
