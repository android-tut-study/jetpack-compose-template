package com.study.compose.ui.landing.components

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.study.compose.ui.common.components.AppIcon
import kotlinx.coroutines.delay


@Composable
fun LandingIcon(
    modifier: Modifier = Modifier,
    timeOut: Long = 1000L,
    onAnimatedEnd: () -> Unit
) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.5f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(timeOut)
        onAnimatedEnd()
    }

    AppIcon(
        resId = com.study.compose.ui.common.R.drawable.logo,
        modifier = modifier
            .size(96.dp)
            .scale(scale.value)
    )
}