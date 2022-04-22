package com.study.compose.core.usecase

import com.study.compose.core.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<Params : Any, Type : Any>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(params: Params): Flow<Result<Type>> =
        execute(params).catch { e -> emit(Result.Failure(Exception(e))) }
            .flowOn(coroutineDispatcher)

    operator fun invoke(): Flow<Result<Type>> =
        execute(null).catch { e -> emit(Result.Failure(Exception(e))) }
            .flowOn(coroutineDispatcher)


    protected abstract fun execute(params: Params?): Flow<Result<Type>>
}