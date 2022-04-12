package com.study.compose.ui.home.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme

@Composable
fun ShrineScaffold(
    topBar: @Composable () -> Unit = { ShrineTopBar() },
    drawerContent: @Composable ColumnScope.() -> Unit = { ShrineDrawer() },
    content: @Composable (PaddingValues) -> Unit
) {
    ShrineComposeTheme {
        Scaffold(
            topBar = topBar,
            drawerContent = drawerContent,
            content = content,
            drawerElevation = 0.dp
        )
    }
}
