package com.example.ui.qr.interactor.state

import com.study.compose.ui.state.UiStatePartialChange

data class QrViewState(
    val currentCode: String,
    val torchEnable: Boolean
) {

    companion object {
        fun initial() = QrViewState(torchEnable = false, currentCode = "")
    }
}

sealed interface QrPartialChange : UiStatePartialChange<QrViewState>

sealed interface CameraAction : QrPartialChange {
    override fun reduce(vs: QrViewState): QrViewState = when (this) {
        CameraSwitch -> TODO()
        ToggleTorch -> vs.copy(torchEnable = !vs.torchEnable)
    }

    object ToggleTorch : CameraAction
    object CameraSwitch : CameraAction
}

data class CodeDetected(val code: String): QrPartialChange {
    override fun reduce(vs: QrViewState): QrViewState = vs.copy(currentCode = code)
}
