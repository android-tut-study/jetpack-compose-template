package com.study.compose.ui.home.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShrineScaffold(
    topBar: @Composable () -> Unit = { ShrineTopBar() },
    drawerContent: @Composable () -> Unit = { ShrineDrawer() },
    content: @Composable () -> Unit
) {
    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    BackdropScaffold(
        appBar = topBar,
        scaffoldState = backdropState,
        backLayerContent = drawerContent,
        frontLayerContent = content,
        frontLayerShape = MaterialTheme.shapes.large,
        frontLayerElevation = 16.dp,
    )
}

@Preview
@Composable
fun ShrineScaffoldPreview() {

    ShrineComposeTheme {
        ShrineScaffold {
            Text(text = "Test Test")
        }
    }
}
