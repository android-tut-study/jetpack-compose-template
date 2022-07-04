package com.study.compose.usecase.carts

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.domain.model.CartDomain
import com.study.compose.core.result.Result
import com.study.compose.core.usecase.FlowUseCase
import com.study.domain.carts.repository.CartRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class GetAllCartsUseCase(
    dispatchers: CoroutineDispatchers,
    private val cartRepo: CartRepo
) : FlowUseCase<Nothing, List<CartDomain>>(dispatchers.io) {
    override fun execute(params: Nothing?): Flow<Result<List<CartDomain>>> {
        return flow {
            cartRepo.getCarts()
                .onEach { emit(Result.Success(it)) }
                .collect()
        }
    }
}