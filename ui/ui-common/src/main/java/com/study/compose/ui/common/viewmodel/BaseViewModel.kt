package com.study.compose.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import com.example.android.core.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

abstract class BaseViewModel<I> : ViewModel() {
    abstract suspend fun processIntent(intent: I)

    val log = Logger.Builder().dispatcher(Dispatchers.IO).build()

    inline fun <reified I : Any> Flow<I>.logIntent() = onEach {
        val tag = this@BaseViewModel::class.java.canonicalName?.split('.')?.last()
            ?: I::class.java.simpleName
        log.d(tag, ">>> Intent $it")
    }

    override fun onCleared() {
        super.onCleared()
        log.clear()
    }
}