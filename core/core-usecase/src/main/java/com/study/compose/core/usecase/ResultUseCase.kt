package com.study.compose.core.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class ResultUseCase<in Params, Type>(private val dispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(params: Params): Result<Type> = withContext(dispatcher) {
        execute(params)
    }

    abstract fun execute(params: Params): Result<Type>
}