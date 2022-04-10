package com.study.compose.ui.landing.components

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.study.compose.ui.common.components.AppIcon
import kotlinx.coroutines.delay


@Composable
fun LandingIcon(
    modifier: Modifier = Modifier,
    timeOut: Long = 3000L,
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
                    OvershootInterpolator(3f).getInterpolation(it)
                }
            )
        )
        delay(timeOut)
        onAnimatedEnd()
    }

    AppIcon(
        resId = com.study.compose.ui.common.R.drawable.ic_launcher_foreground,
        modifier = modifier.scale(scale.value)
    )
}