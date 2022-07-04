package com.example.android.core.logger

interface ILogger {
    suspend fun d(tag: String, msg: String)
    suspend fun e(tag: String, msg: String)
    suspend fun i(tag: String, msg: String)
    suspend fun w(tag: String, msg: String)

    fun clear()
}