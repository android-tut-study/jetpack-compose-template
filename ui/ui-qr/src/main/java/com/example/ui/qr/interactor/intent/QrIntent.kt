package com.example.ui.qr.interactor.intent

sealed class QrIntent {
    object ToggleTorch : QrIntent()
    data class NotifyTorchState(val enabled: Boolean) : QrIntent()
    data class DetectCode(val code: String) : QrIntent()
}