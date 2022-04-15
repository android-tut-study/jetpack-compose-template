package com.study.compose.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.components.*
import com.study.compose.ui.home.data.SampleCartItems
import com.study.compose.ui.home.view.ProductsContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            ShrineComposeTheme {
                BoxWithConstraints {
                    HomeContent()
                    BottomCart(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        maxHeight = maxHeight,
                        maxWidth = maxWidth,
                        carts = SampleCartItems

                    )
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    onNavigationPressed: () -> Unit = {},
    onFilterPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {}
) {
    ShrineScaffold(
        topBar = {
            ShrineTopBar(
                navIcon = { NavigationIcon(onNavIconPressed = onNavigationPressed) },
                actions = {
                    HomeActionIcon(
                        com.study.compose.ui.common.R.drawable.shr_search,
                        onPressed = onSearchPressed
                    )
                    HomeActionIcon(
                        com.study.compose.ui.common.R.drawable.shr_filter,
                        onPressed = onFilterPressed
                    )
                }
            )
        },
        drawerContent = { NavigationMenus() }
    ) {
        ProductsContent()
    }
}


@Composable
fun NavigationMenus() {
    ShrineDrawer()
}


@Preview
@Composable
fun HomeScreenPreview() {
    ShrineComposeTheme {
        HomeContent()
    }
}