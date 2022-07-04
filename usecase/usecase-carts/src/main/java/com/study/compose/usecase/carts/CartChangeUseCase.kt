package com.study.compose.usecase.carts

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.domain.model.CartChangeType
import com.study.compose.core.domain.model.CartDomain
import com.study.compose.core.result.Result
import com.study.compose.core.usecase.FlowUseCase
import com.study.domain.carts.models.CartChange
import com.study.domain.carts.repository.CartRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class CartChangeUseCase(
    dispatchers: CoroutineDispatchers,
    private val cartRepo: CartRepo
) : FlowUseCase<Nothing, CartChangeUseCase.Change>(dispatchers.io) {

    override fun execute(params: Nothing?): Flow<Result<Change>> = flow {
        cartRepo.getCartRepoState()
            .onEach { repoState ->
                emit(Result.Success(Change(repoState.cartChanged)))
            }.collect()
    }

    data class Change(val cartDomain: CartDomain?, val type: CartChangeType) {
        constructor(cartChange: CartChange) : this(
            cartDomain = cartChange.cart,
            type = cartChange.type
        )
    }
}