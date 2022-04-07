package com.study.compose.shrine.components

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import com.study.compose.ui.common.theme.ShrineComposeTheme

@Composable
fun ShrineScaffold(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    content: @Composable () -> Unit
) {
    ShrineComposeTheme {
        ModalDrawer(
            drawerState = drawerState,
            drawerContent = {
                ShrineDrawer()
            },
            content = content
        )

    }
}
