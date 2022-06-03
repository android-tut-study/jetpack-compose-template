package com.study.compose.usecase.products

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.domain.model.ProductDomain
import com.study.compose.core.result.Result
import com.study.compose.core.usecase.FlowUseCase
import com.study.compose.domain.products.repository.ProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class GetProductsUseCase(
    dispatchers: CoroutineDispatchers,
    private val repo: ProductRepo
) :
    FlowUseCase<Nothing, List<ProductDomain>>(dispatchers.computation) {
    override fun execute(params: Nothing?): Flow<Result<List<ProductDomain>>> {
        return flow {
            repo.getCurrentProducts().onEach {
                emit(Result.Success(it))
            }.collect()
        }
    }
}