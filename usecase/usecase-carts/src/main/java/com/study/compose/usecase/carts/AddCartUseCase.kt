package com.study.compose.usecase.carts

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.CartDomain
import com.study.compose.core.usecase.ResultUseCase
import com.study.domain.carts.models.Cart
import com.study.domain.carts.repository.CartRepo
import com.study.compose.core.result.Result

class AddCartUseCase(
    dispatchers: CoroutineDispatchers,
    private val repo: CartRepo,
    private val cartDomainToEntity: Mapper<CartDomain, Cart>
) : ResultUseCase<CartDomain, Boolean>(dispatchers.io) {

    override suspend fun execute(params: CartDomain): Result<Boolean> {
        val result = repo.addCart(cartDomainToEntity(params))
        return if (result > 0) {
            Result.Success(true)
        } else {
            Result.Failure(Exception("Failed to Add to Cart"))
        }
    }
}