package com.study.compose.core.usecase

import com.study.compose.core.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class ResultUseCase<in Params, Type>(private val dispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(params: Params): Result<Type> = withContext(dispatcher) {
        execute(params)
    }

    abstract suspend fun execute(params: Params): Result<Type>
}
