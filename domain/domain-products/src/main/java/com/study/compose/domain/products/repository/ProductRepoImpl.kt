package com.study.compose.domain.products.repository

import com.study.compose.core.domain.model.ProductDomain
import com.study.compose.domain.products.di.ProductApiService
import kotlinx.coroutines.flow.Flow

class ProductRepoImpl(apiService: ProductApiService): ProductRepo {
    override fun fetchProducts(): Flow<List<ProductDomain>> {
        TODO("Not yet implemented")
    }
}