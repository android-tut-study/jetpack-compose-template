package com.study.compose.ui.home.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.theme.ShrineComposeTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShrineScaffold(
    topBar: @Composable () -> Unit = { ShrineTopBar() },
    drawerContent: @Composable () -> Unit = { ShrineDrawer() },
    scaffoldState: BackdropScaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed),
    content: @Composable () -> Unit
) {
    BackdropScaffold(
        appBar = topBar,
        scaffoldState = scaffoldState,
        backLayerContent = drawerContent,
        frontLayerContent = content,
        frontLayerShape = MaterialTheme.shapes.large,
        frontLayerElevation = 16.dp,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ShrineScaffoldPreview() {
    ShrineComposeTheme {
        ShrineScaffold {
            Text(text = "Test Test")
        }
    }
}
