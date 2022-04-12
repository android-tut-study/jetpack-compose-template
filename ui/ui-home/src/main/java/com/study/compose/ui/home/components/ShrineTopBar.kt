package com.study.compose.ui.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.home.R

@Composable
fun ShrineTopBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = { TopHeader() },
    navIcon: @Composable () -> Unit = { },
    actions: @Composable RowScope.() -> Unit = { }
) = TopAppBar(
    title = title,
    navigationIcon = navIcon,
    modifier = modifier,
    elevation = 0.dp,
    actions = actions
)

@Composable
fun TopHeader() {
    Text(
        text = stringResource(id = com.study.compose.ui.common.R.string.txt_shrine),
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun NavigationIcon(onNavIconPressed: () -> Unit = {}) {
    Icon(
        modifier = Modifier
            .clickable { onNavIconPressed() }
            .padding(12.dp),
        painter = painterResource(id = com.study.compose.ui.common.R.drawable.shr_branded_menu),
        contentDescription = "branded menu"
    )
}

@Composable
fun HomeActionIcon(
    @DrawableRes resId: Int,
    onPressed: () -> Unit = {},
    contentDescription: String? = null
) {
    Icon(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        tint = MaterialTheme.colors.onSurface,
        modifier = Modifier
            .clickable { onPressed() }
            .padding(12.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ShrineTopBarPreview() {
    ShrineComposeTheme {
        ShrineTopBar()
    }
}

