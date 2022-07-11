package com.example.ui.qr.components

import android.util.Log
import android.util.Size
import androidx.camera.core.*
import androidx.camera.core.AspectRatio.RATIO_16_9
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.ui.qr.R
import com.example.ui.qr.analyzer.QrAnalyzer
import com.study.compose.ui.common.components.ExpandableText
import com.study.compose.ui.common.theme.ShrineComposeTheme
import com.study.compose.ui.common.utils.textSelectionColors

@Composable
fun Scanner(
    modifier: Modifier,
    size: Dp,
    currentCode: String,
    torchEnable: Boolean,
    onQrDetect: (String) -> Unit = {},
    onCodeCopied: (String) -> Unit,
    onTorchStateChange: (enabled: Boolean) -> Unit
) {
    val cameraOffsetWithBorder = 30.dp
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .size(size)
                .offset(y = (-20).dp)
        ) {
            CameraPreview(
                modifier = Modifier
                    .padding(cameraOffsetWithBorder)
                    .fillMaxSize(),
                onDetected = onQrDetect,
                torchEnable = torchEnable,
                onTorchStateChange = onTorchStateChange
            )

            ScannerOverlay(
                modifier = Modifier.align(Alignment.TopCenter),
                containerSize = size,
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
        ConcealableCode(
            code = currentCode,
            modifier = Modifier
                .padding(horizontal = cameraOffsetWithBorder)
                .fillMaxWidth()
                .wrapContentHeight(),
            onCodeCopied = onCodeCopied
        )

    }
}

@Composable
fun CopyClipboard(modifier: Modifier = Modifier, onCopyPressed: () -> Unit) {
    IconButton(
        onClick = onCopyPressed, modifier = modifier
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_copy_24),
            contentDescription = "Copy to Clipboard"
        )
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier,
    torchEnable: Boolean,
    onTorchStateChange: (enabled: Boolean) -> Unit,
    onDetected: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var code by remember {
        mutableStateOf("")
    }
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    var camera: Camera? by remember { mutableStateOf(null) }

    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = { ctx ->
            Log.e("ANNX", "ReCreate Camera Instance")
            val executor = ContextCompat.getMainExecutor(ctx)
            val previewView = PreviewView(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                // TODO check device has at least 1 camera
                val preview = androidx.camera.core.Preview.Builder()
                    .setTargetAspectRatio(RATIO_16_9)
                    .build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                val selector = CameraSelector.Builder().requireLensFacing(LENS_FACING_BACK).build()

                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(Size(previewView.width, previewView.height))
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

                val cameraLoaded = cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    imageAnalysis,
                    preview,
                )
                observerTorchState(
                    lifecycleOwner = lifecycleOwner,
                    cameraInfo = cameraLoaded.cameraInfo,
                    onTorchStateChange = onTorchStateChange
                )
                camera = cameraLoaded
            }, executor)
            previewView
        },
        modifier = modifier,
        update = {
            Log.e("ANNX", "update Camera $camera")
            camera?.apply {
                cameraControl.enableTorch(torchEnable)
            }
        }
    )
}

private fun observerTorchState(
    lifecycleOwner: LifecycleOwner,
    cameraInfo: CameraInfo,
    onTorchStateChange: (enabled: Boolean) -> Unit
) {
    cameraInfo.torchState.observe(lifecycleOwner) { state ->
        onTorchStateChange(state == TorchState.ON)
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ConcealableCode(modifier: Modifier = Modifier, code: String, onCodeCopied: (String) -> Unit) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = code.isNotEmpty(),
        enter = slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        modifier = modifier
            .fillMaxWidth()
    ) {
        AnimatedContent(
            targetState = code,
            transitionSpec = {
                if (!targetState.contentEquals(initialState)) {
                    slideInVertically { height -> height } + fadeIn(initialAlpha = 0.3f) with
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    SizeTransform(clip = true)
                )
            },
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .clip(MaterialTheme.shapes.small)
                .background(color = MaterialTheme.colors.primary),
            contentAlignment = Alignment.CenterStart
        ) { targetState ->
            Row(modifier = Modifier.wrapContentHeight()) {
                val selectionColor =
                    textSelectionColors(MaterialTheme.colors.onSurface.copy(alpha = 0.8f))
                CompositionLocalProvider(LocalTextSelectionColors provides selectionColor) {
                    SelectionContainer(modifier = Modifier.weight(1f)) {
                        ExpandableText(
                            modifier = Modifier.padding(10.dp),
                            text = targetState,
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colors.onPrimary
                            ),
                            collapsedMaxLine = 1,
                            showMoreStyle = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Normal,
                                color = MaterialTheme.colors.onPrimary,
                                fontSize = MaterialTheme.typography.subtitle1.fontSize
                            ),
                            showMoreText = " ➘ more",
                            showLessText = " ➚ less",
                        )
                    }
                }
                CopyClipboard(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(32.dp),
                    onCopyPressed = {
                        onCodeCopied(code)
                    }
                )
            }
        }
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

@Preview
@Composable
fun LoadingPreview() {
    ShrineComposeTheme {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            ScannerOverlay(Modifier.align(Alignment.Center), containerSize = 200.dp)
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun CodePreview() {
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.surface, modifier = Modifier.height(80.dp)) {

            Column {

                ConcealableCode(
                    code = "Code",
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    onCodeCopied = {}
                )
                Spacer(modifier = Modifier.height(10.dp))
                ConcealableCode(
                    code = "code".repeat(10),
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    onCodeCopied = {}
                )
            }
        }
    }
}