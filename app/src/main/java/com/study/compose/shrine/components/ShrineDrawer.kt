package com.study.compose.shrine.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ColumnScope.ShrineDrawer() {
    // TODO create drawer layout
    // Create Header
    DrawerHeader()

}

@Composable
fun DrawerHeader() {
    Text(text = "This is Drawer Header", style = MaterialTheme.typography.h5)
}
