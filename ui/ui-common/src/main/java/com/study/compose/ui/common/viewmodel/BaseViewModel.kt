package com.study.compose.ui.common.viewmodel

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<I>: ViewModel() {
    abstract suspend fun processIntent(intent: I)
}