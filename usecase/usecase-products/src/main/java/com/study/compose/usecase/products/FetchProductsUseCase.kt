package com.study.compose.usecase.products

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.domain.model.ProductDomain
import com.study.compose.core.result.Result
import com.study.compose.core.usecase.FlowUseCase
import com.study.compose.domain.products.repository.ProductRepo
import kotlinx.coroutines.flow.*

class FetchProductsUseCase(
    dispatcher: CoroutineDispatchers,
    private val repo: ProductRepo
) : FlowUseCase<Nothing, List<ProductDomain>>(dispatcher.io) {

    override fun execute(params: Nothing?): Flow<Result<List<ProductDomain>>> {
        return flow {
            repo.fetchProducts()
                .onEach { emit(Result.Success(data = it)) }
                .catch { emit(Result.Failure(throwable = it)) }
                .collect()
        }
    }
}