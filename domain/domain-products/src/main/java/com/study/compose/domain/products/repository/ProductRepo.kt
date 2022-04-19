package com.study.compose.domain.products.repository

import com.study.compose.core.domain.model.ProductDomain
import kotlinx.coroutines.flow.Flow

interface ProductRepo {
    fun fetchProducts(): Flow<List<ProductDomain>>
}