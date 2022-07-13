package com.study.compose.usecase.products.di

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.domain.products.repository.ProductRepo
import com.study.compose.usecase.products.FetchProductsUseCase
import com.study.compose.usecase.products.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun providesFetchProductUseCase(dispatchers: CoroutineDispatchers, productRepo: ProductRepo) =
        FetchProductsUseCase(
            dispatcher = dispatchers,
            repo = productRepo
        )

    @Provides
    fun providesGetProductsUseCase(dispatchers: CoroutineDispatchers, productRepo: ProductRepo) =
        GetProductsUseCase(
            dispatchers = dispatchers,
            repo = productRepo
        )
}