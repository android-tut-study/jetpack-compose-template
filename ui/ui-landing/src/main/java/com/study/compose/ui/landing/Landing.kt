package com.study.compose.ui.landing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.study.compose.ui.landing.components.LandingIcon

@Composable
fun LandingScreen(onLoadedEnd: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LandingIcon {
            // TODO navigate Landing to Home
            onLoadedEnd()
        }
    }
}