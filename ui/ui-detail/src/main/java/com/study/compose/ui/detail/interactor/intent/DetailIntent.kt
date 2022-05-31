package com.study.compose.ui.detail.interactor.intent

sealed class DetailIntent {
    data class Initial(val productId: Int) : DetailIntent()
    object GetProducts : DetailIntent()
}