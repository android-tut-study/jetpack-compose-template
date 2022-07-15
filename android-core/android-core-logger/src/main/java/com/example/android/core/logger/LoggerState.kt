package com.example.android.core.logger

data class LoggerState(
    val logLevel: LogLevel = LogLevel.D,
    val tag: String? = null,
    val msg: String
)

enum class LogLevel {
    D,
    W,
    E,
    I
}
