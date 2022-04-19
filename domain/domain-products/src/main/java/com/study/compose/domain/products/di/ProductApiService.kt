package com.study.compose.domain.products.di

import com.study.compose.domain.products.model.response.ProductsResponse
import retrofit2.http.GET

interface ProductApiService {

    @GET("/products")
    suspend fun fetchProducts(): List<ProductsResponse>
}