package com.study.compose.usecase.carts

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.CartDomain
import com.study.compose.core.usecase.NoResultUseCase
import com.study.domain.carts.models.Cart
import com.study.domain.carts.repository.CartRepo

class RemoveCartUseCase(
    dispatchers: CoroutineDispatchers,
    private val repo: CartRepo,
    private val cartDomainToEntity: Mapper<CartDomain, Cart>
) : NoResultUseCase<CartDomain>(dispatchers.io) {

    override suspend fun execute(params: CartDomain) {
        repo.removeCart(cartDomainToEntity(params))
    }
}