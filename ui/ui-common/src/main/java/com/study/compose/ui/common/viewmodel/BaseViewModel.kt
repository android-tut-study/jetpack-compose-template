package com.study.compose.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import com.example.android.core.Logger
import com.example.android.core.logger.LogLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform

abstract class BaseViewModel<I> : ViewModel() {
    abstract suspend fun processIntent(intent: I)

    val log = Logger.Builder().dispatcher(Dispatchers.IO).build()

    inline fun <reified I : Any> Flow<I>.logIntent() = transform { value ->
        val tag = this@BaseViewModel::class.java.canonicalName?.split('.')?.last()
            ?: I::class.java.simpleName
        log.d(tag, ">>> Intent $value")
        return@transform emit(value)
    }

    override fun onCleared() {
        log.clear()
        super.onCleared()
    }
}