package com.example.ui.qr

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.ui.qr.components.Header
import com.example.ui.qr.components.ScanAction
import com.example.ui.qr.components.Scanner
import com.study.compose.ui.common.theme.ShrineComposeTheme

@Composable
fun Qr(onClosed: () -> Unit) {
    val context = LocalContext.current
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasCamPermission = granted }
    )

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }
    ShrineComposeTheme {
        Surface(color = MaterialTheme.colors.surface, modifier = Modifier.fillMaxSize()) {
            Qr(
                hasCameraPermission = hasCamPermission,
                onClosed = onClosed
            )
        }
    }
}

@Composable
fun Qr(
    hasCameraPermission: Boolean,
    onClosed: () -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val scannerSize = screenWidth * 4 / 5
    val actionHeight = 80.dp

    var currentQrCode by remember {
        mutableStateOf("")
    }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        if (hasCameraPermission) {
            Scanner(
                modifier = Modifier.offset(y = (screenHeight - actionHeight - scannerSize) / 2).align(Alignment.TopCenter),
                currentCode = currentQrCode,
                size = scannerSize,
            ) { code ->
                currentQrCode = code
            }
        }
        ScanAction(
            modifier = Modifier
                .clip(CutCornerShape(10.dp))
                .background(MaterialTheme.colors.background.copy(alpha = 0.8f))
                .fillMaxWidth()
                .height(actionHeight)
                .align(Alignment.BottomCenter)
        )
        Header(modifier = Modifier.align(Alignment.TopStart)) {
            onClosed()
        }
    }
}




