package com.example.ui.qr.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.ui.qr.interactor.intent.QrIntent
import com.example.ui.qr.interactor.state.CameraAction
import com.example.ui.qr.interactor.state.CodeDetected
import com.example.ui.qr.interactor.state.QrViewState
import com.study.compose.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor() : BaseViewModel<QrIntent>() {

    private val _intentChannel = MutableSharedFlow<QrIntent>()
    val viewState: MutableStateFlow<QrViewState>

    init {
        val initialVS = QrViewState.initial()
        viewState = MutableStateFlow(initialVS)
        _intentChannel
            .logIntent()
            .toViewStateChange()
            .scan(initialVS) { vs, change -> change.reduce(vs) }
            .onEach { viewState.emit(it) }
            .launchIn(viewModelScope)
    }

    override suspend fun processIntent(intent: QrIntent) = _intentChannel.emit(intent)

    @OptIn(FlowPreview::class)
    private fun Flow<QrIntent>.toViewStateChange() = merge(
        filterIsInstance<QrIntent.ToggleTorch>()
            .flatMapConcat { toggleTorch() },
        filterIsInstance<QrIntent.DetectCode>()
            .flatMapConcat { qrCode(it.code) },
        filterIsInstance<QrIntent.NotifyTorchState>()
            .flatMapConcat { checkTorchState(it.enabled) },
    )

    private fun qrCode(code: String) = flow {
        emit(CodeDetected(code))
    }

    private fun toggleTorch() = flow {
        emit(CameraAction.ToggleTorch)
    }

    private fun checkTorchState(enabled: Boolean) = flow {
        val currentQrTorchState = viewState.value.torchEnable
        if (currentQrTorchState != enabled) {
            emit(CameraAction.ToggleTorch)
        }
    }
}