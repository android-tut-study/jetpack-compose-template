package com.study.compose.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.components.HomeActionIcon
import com.study.compose.ui.home.components.NavigationIcon
import com.study.compose.ui.home.components.ShrineScaffold
import com.study.compose.ui.home.components.ShrineTopBar
import com.study.compose.ui.home.view.HomeContent
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
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(
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
        }
    ) { paddingValues ->
        HomeContent(paddingValues)
    }
}