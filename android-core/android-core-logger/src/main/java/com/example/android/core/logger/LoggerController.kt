package com.example.android.core.logger

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoggerController(
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ILogger {

    private val _channel = MutableSharedFlow<LoggerState>()
    private val _job = Job()
    private val scope = CoroutineScope(dispatcher + _job)

    init {
        _channel
            .onEach { log ->
                when (log.logLevel) {
                    LogLevel.D -> Log.d(log.tag, log.msg)
                    LogLevel.W -> Log.w(log.tag, log.msg)
                    LogLevel.E -> Log.e(log.tag, log.msg)
                    LogLevel.I -> Log.i(log.tag, log.msg)
                }
            }
            .catch { e ->
                if (e is CancellationException) {
                    Log.w(ILogger::class.java.simpleName, "Logger Cancel")
                } else {
                    Log.e(ILogger::class.java.simpleName, "exception ${e.cause}")
                }
            }
            .launchIn(scope)

    }

    override suspend fun d(tag: String, msg: String) {
        _channel.emit(LoggerState(LogLevel.D, tag, msg))
    }

    override suspend fun e(tag: String, msg: String) {
        _channel.emit(LoggerState(LogLevel.E, tag, msg))
    }

    override suspend fun i(tag: String, msg: String) {
        _channel.emit(LoggerState(LogLevel.I, tag, msg))
    }

    override suspend fun w(tag: String, msg: String) {
        _channel.emit(LoggerState(LogLevel.W, tag, msg))
    }

    override fun clear() {
        // TODO add key handle cancelable flow (because log function is not cancelable)
        scope.cancel(CancellationException("Cancel Logger"))
    }
}