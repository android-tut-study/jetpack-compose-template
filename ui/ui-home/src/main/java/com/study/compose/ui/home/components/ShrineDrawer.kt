package com.study.compose.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.data.Category

@Composable
fun ShrineDrawer(
    modifier: Modifier = Modifier,
    onMenuSelected: () -> Unit = {},
) {
    // TODO create drawer layout
    // Create Header
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val categories = Category.values()
        categories.forEachIndexed { _, category ->
            DrawerMenu(text = category.name)
        }

    }

}

@Composable
fun DrawerMenu(
    text: String,
    action: () -> Unit = {}
) {
    Box(
        modifier = Modifier.height(44.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerMenuPreview() {
    ShrineComposeTheme {
        DrawerMenu(text = "Menu Preview")
    }
}

@Preview(showBackground = true)
@Composable
fun ShrineDrawerPreview() {
    ShrineComposeTheme {
        ShrineDrawer()
    }
}
