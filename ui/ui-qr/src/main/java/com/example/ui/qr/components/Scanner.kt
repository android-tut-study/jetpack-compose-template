package com.example.ui.qr.components

import androidx.camera.core.AspectRatio.RATIO_16_9
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.ui.qr.R
import com.example.ui.qr.analyzer.QrAnalyzer
import com.study.compose.ui.common.theme.ShrineComposeTheme

@Composable
fun Scanner(modifier: Modifier, currentCode: String, onQrDetect: (String) -> Unit = {}) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val scannerSize = screenWidth * 4 / 5
    val cameraOffsetWithBorder = 30.dp
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .size(scannerSize)
                .offset(y = (-20).dp)
        ) {
            CameraPreview(
                modifier = Modifier
                    .padding(cameraOffsetWithBorder)
                    .fillMaxSize(),
                onDetected = onQrDetect
            )

            ScannerOverlay(
                modifier = Modifier.align(Alignment.TopCenter),
                containerSize = scannerSize,
                height = 8.dp,
                verticalDpOffset = cameraOffsetWithBorder,
                horizontalDpOffset = cameraOffsetWithBorder
            )

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_scan_border),
                contentDescription = "Scanner border"
            )
        }
        ConcealableCode(code = currentCode)
    }
}

@Composable
fun CameraPreview(modifier: Modifier, onDetected: (String) -> Unit = {}) {
    val context = LocalContext.current
    var code by remember {
        mutableStateOf("")
    }
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = { ctx ->
            val executor = ContextCompat.getMainExecutor(ctx)
            val previewView = PreviewView(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = androidx.camera.core.Preview.Builder()
                    .setTargetAspectRatio(RATIO_16_9)
                    .build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val selector = CameraSelector.Builder().requireLensFacing(LENS_FACING_BACK).build()

                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetAspectRatio(RATIO_16_9)
                    .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(ctx),
                    QrAnalyzer { result ->
                        if (code != result) {
                            code = result
                            onDetected(result)
                        }
                    }
                )

                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    imageAnalysis,
                    preview,
                )
            }, executor)

            previewView
        },
        modifier = modifier
    )
}

@Composable
fun ConcealableCode(code: String) {
    AnimatedVisibility(
        visible = code.isNotEmpty(),
        enter = EnterTransition.None,
        exit = ExitTransition.None
    ) {
        Text(
            text = code,
            style = MaterialTheme.typography.subtitle1.copy(fontStyle = FontStyle.Italic)
        )
    }
}

@Composable
fun ScannerOverlay(
    modifier: Modifier = Modifier,
    containerSize: Dp,
    height: Dp = 1.dp,
    verticalDpOffset: Dp = 0.dp,
    horizontalDpOffset: Dp = 0.dp
) {
    val infinity = rememberInfiniteTransition()

    val yOffset by infinity.animateValue(
        initialValue = verticalDpOffset,
        targetValue = containerSize - verticalDpOffset - height,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        typeConverter = Dp.VectorConverter
    )

    val alpha by infinity.animateFloat(
        initialValue = 0.4f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
    )

    val heightOffset by infinity.animateValue(
        initialValue = height / 2,
        targetValue = height,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(width = containerSize - horizontalDpOffset * 2, height = heightOffset)
            .offset(y = yOffset)
            .alpha(alpha)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.background,
                        MaterialTheme.colors.secondary,
                        MaterialTheme.colors.surface,
                    )
                )
            )
            .then(modifier)
    )
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun LoadingPreview() {
    ShrineComposeTheme {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            ScannerOverlay(Modifier.align(Alignment.Center), containerSize = 200.dp)
        }
    }
}