package com.study.compose.domain.products.repository

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.ProductDomain
import com.study.compose.domain.products.di.ProductApiService
import com.study.compose.domain.products.model.response.ProductsResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class ProductRepoImpl(
    private val apiService: ProductApiService,
    private val dispatcher: CoroutineDispatchers,
    private val productDomainMapper: Mapper<ProductsResponse, ProductDomain>
) : ProductRepo {

    private val _productsState = MutableStateFlow<Change>(Change.Refresh(emptyList()))

    override fun fetchProducts(): Flow<List<ProductDomain>> = flow {
        val remoteProducts = fetchRemoteProducts()
        _productsState
            .scan(Change.Refresh(remoteProducts)) { acc, value ->
                when (value) {
                    is Change.Refresh -> value.copy(products = acc.products)
                }
            }
            .onEach { emit(it.products) }
            .collect()
    }

    private suspend fun fetchRemoteProducts(): List<ProductDomain> = withContext(dispatcher.io) {
        val productsResponse = apiService.fetchProducts()
        productsResponse.map { productDomainMapper(it) }
    }

    private sealed class Change {
        data class Refresh(val products: List<ProductDomain>) : Change()
    }
}