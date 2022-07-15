package com.example.android.core

import android.util.Log
import com.example.android.core.logger.ILogger
import com.example.android.core.logger.LoggerController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Logger class used for SharedFlow. Prevent intent from emitter while the emit function is suspended
 */
class Logger private constructor(dispatcher: CoroutineDispatcher) : ILogger {

    private constructor(builder: Builder) : this(builder.dispatcher)

    var controller: LoggerController

    init {
        controller = LoggerController(dispatcher)
    }

    override suspend fun d(tag: String, msg: String) {
        controller.d(tag, msg)
    }

    override suspend fun e(tag: String, msg: String) {
        controller.e(tag, msg)
    }

    override suspend fun i(tag: String, msg: String) {
        controller.i(tag, msg)
    }

    override suspend fun w(tag: String, msg: String) {
        controller.w(tag, msg)
    }

    override fun clear() {
        Log.d(Logger::class.simpleName, " >>> Logger Cancel ! <<<")
        controller.clear()
    }

    class Builder {
        var dispatcher: CoroutineDispatcher = Dispatchers.Default

        fun dispatcher(coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default): Builder =
            this.apply { dispatcher = coroutineDispatcher }

        fun build() = Logger(this).also {
            Log.d(Logger::class.simpleName, " >>> Logger Created ! <<<")
        }
    }

}
