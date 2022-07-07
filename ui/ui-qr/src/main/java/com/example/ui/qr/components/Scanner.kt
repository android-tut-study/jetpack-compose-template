package com.example.ui.qr.components

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.ui.qr.R
import com.example.ui.qr.analyzer.QrAnalyzer

@Composable
fun Scanner(modifier: Modifier, currentCode: String, onQrDetect: (String) -> Unit = {}) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val scannerSize = screenWidth * 4 / 5
    Column(modifier = modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(scannerSize)
                .offset(y = (-20).dp)
        ) {
            CameraPreview(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxSize(),
                onDetected = onQrDetect
            )
            Icon(
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
                val preview = androidx.camera.core.Preview.Builder().build().also {
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