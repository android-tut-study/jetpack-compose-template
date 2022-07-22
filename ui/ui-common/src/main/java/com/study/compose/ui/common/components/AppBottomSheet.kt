package com.study.compose.ui.common.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppBottomSheet(
    modifier: Modifier = Modifier,
    bottomSheetState: ModalBottomSheetState,
    sheetContent: @Composable ColumnScope.() -> Unit,
    sheetShape: Shape = MaterialTheme.shapes.large,
    backdropContent: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetContent = {
            sheetContent()
        },
        content = backdropContent,
        sheetState = bottomSheetState,
        sheetShape = sheetShape
    )
}