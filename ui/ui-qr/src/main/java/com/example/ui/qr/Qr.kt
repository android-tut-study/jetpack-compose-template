package com.example.ui.qr

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.qr.components.Header
import com.example.ui.qr.components.ScanAction
import com.example.ui.qr.components.Scanner
import com.example.ui.qr.interactor.intent.QrIntent
import com.example.ui.qr.interactor.state.QrViewState
import com.example.ui.qr.viewmodel.QrViewModel
import com.study.compose.ui.common.theme.ShrineComposeTheme
import kotlinx.coroutines.launch

@Composable
fun Qr(onClosed: () -> Unit, onImageSelectPressed: () -> Unit) {
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

//    ShrineComposeTheme {
        if (hasCamPermission) {
            Qr(
                viewModel = hiltViewModel(),
                onClosed = onClosed,
                onImageSelectPressed = onImageSelectPressed
            )
        } else {
            // TODO Show required camera permission UI
            Text(text = "Please Enable Camera Permission")
        }
//    }
}

@Composable
fun Qr(viewModel: QrViewModel, onClosed: () -> Unit, onImageSelectPressed: () -> Unit) {
    val viewState by viewModel.viewState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val clipboardManager = LocalClipboardManager.current
    Qr(
        viewState = viewState,
        onFlashPressed = {
            coroutineScope.launch {
                viewModel.processIntent(QrIntent.ToggleTorch)
            }
        },
        onClosed = onClosed,
        onCodeDetected = { code ->
            coroutineScope.launch {
                viewModel.processIntent(QrIntent.DetectCode(code))
            }
        },
        onTorchStateChange = { state ->
            coroutineScope.launch {
                viewModel.processIntent(QrIntent.NotifyTorchState(state))
            }
        },
        onImageSelectPressed = onImageSelectPressed,
        onCodeCopied = { code ->
            // TODO transform copy icon to copied + show toast
            clipboardManager.setText(AnnotatedString(code))
        }
    )
}

@Composable
fun Qr(
    viewState: QrViewState,
    onFlashPressed: () -> Unit,
    onCodeDetected: (code: String) -> Unit,
    onTorchStateChange: (enabled: Boolean) -> Unit,
    onImageSelectPressed: () -> Unit,
    onClosed: () -> Unit,
    onCodeCopied: (String) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val scannerSize = screenWidth * 0.7f
    val actionHeight = 80.dp

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Scanner(
            modifier = Modifier
                .offset(y = (screenHeight - actionHeight - scannerSize) / 2)
                .align(Alignment.TopCenter),
            currentCode = viewState.currentCode,
            size = scannerSize,
            torchEnable = viewState.torchEnable,
            onQrDetect = onCodeDetected,
            onTorchStateChange = onTorchStateChange,
            onCodeCopied = onCodeCopied
        )
        ScanAction(
            modifier = Modifier
                .clip(CutCornerShape(10.dp))
                .background(MaterialTheme.colors.background.copy(alpha = 0.8f))
                .fillMaxWidth()
                .height(actionHeight)
                .align(Alignment.BottomCenter),
            onFlashPressed = onFlashPressed,
            onImageSearched = onImageSelectPressed,
        )
        Header(modifier = Modifier.align(Alignment.TopStart)) {
            onClosed()
        }
    }
}




