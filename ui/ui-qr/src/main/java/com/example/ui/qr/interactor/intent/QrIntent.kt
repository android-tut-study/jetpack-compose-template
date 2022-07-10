package com.example.ui.qr.interactor.intent

sealed class QrIntent {
    object ToggleTorch : QrIntent()
    data class DetectCode(val code: String) : QrIntent()
}