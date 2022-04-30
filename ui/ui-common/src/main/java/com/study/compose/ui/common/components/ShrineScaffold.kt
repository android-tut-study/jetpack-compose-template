package com.study.compose.ui.common.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShrineScaffold(
    topBar: @Composable () -> Unit = { },
    backLayerContent: @Composable () -> Unit = {},
    scaffoldState: BackdropScaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed),
    gesturesEnabled: Boolean = false,
    content: @Composable () -> Unit
) {
    BackdropScaffold(
        appBar = topBar,
        scaffoldState = scaffoldState,
        backLayerContent = backLayerContent,
        frontLayerContent = content,
        frontLayerShape = MaterialTheme.shapes.large,
        frontLayerElevation = 16.dp,
        gesturesEnabled = gesturesEnabled
    )
}

@Composable
fun ShrineScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = { },
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    drawerContent: @Composable ColumnScope.() -> Unit = {},
    content: @Composable (paddingValue: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        scaffoldState = scaffoldState,
        drawerContent = drawerContent,
        content = content
    )
}

@Composable
fun ShrineTopBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = { },
    navIcon: @Composable () -> Unit = { },
    actions: @Composable RowScope.() -> Unit = { }
) = TopAppBar(
    title = title,
    navigationIcon = navIcon,
    modifier = modifier,
    elevation = 0.dp,
    actions = actions
)

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ShrineScaffoldPreview() {
    ShrineComposeTheme {
        ShrineScaffold(backLayerContent = {}) {
            Text(text = "Test Test")
        }
    }
}