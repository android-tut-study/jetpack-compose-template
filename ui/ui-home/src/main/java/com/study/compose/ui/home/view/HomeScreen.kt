package com.study.compose.ui.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductsContent() {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text(text = "HomeContent")

    }
//    Surface(
//        modifier = Modifier
//            .background(MaterialTheme.colors.primary)
//            .fillMaxSize(),
//        shape = MaterialTheme.shapes.large,
//        elevation = 4.dp,
//    ) {
//        Box(
//            modifier = Modifier
//                .background(MaterialTheme.colors.surface)
//                .fillMaxSize()
//        ) {
//            // TODO show grid layout items
//            Text(text = "HomeContent")
//        }
//    }

}