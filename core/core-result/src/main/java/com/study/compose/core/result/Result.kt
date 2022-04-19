package com.study.compose.core.result

sealed class Result<out T> {

    open fun get(): T? = null

    fun getOrThrow(): T = when (this) {
        is Success -> get()
        is Failure -> throw throwable
    }

    data class Success<T>(val data: T) : Result<T>() {
        override fun get(): T = data
    }

    data class Failure(val throwable: Throwable) : Result<Nothing>()
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null