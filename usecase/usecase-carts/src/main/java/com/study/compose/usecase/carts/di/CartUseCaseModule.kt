package com.study.compose.usecase.carts.di

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.CartDomain
import com.study.compose.usecase.carts.*
import com.study.compose.usecase.carts.mappers.CartDomainToEntityMapper
import com.study.domain.carts.models.Cart
import com.study.domain.carts.repository.CartRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object CartUseCaseModule {

    @Provides
    fun providesGetAllCartsUseCase(dispatchers: CoroutineDispatchers, repo: CartRepo) =
        GetAllCartsUseCase(
            dispatchers = dispatchers,
            cartRepo = repo
        )

    @Provides
    fun providesCartChangeUseCase(dispatchers: CoroutineDispatchers, repo: CartRepo) =
        CartChangeUseCase(
            dispatchers = dispatchers,
            cartRepo = repo
        )

    @Provides
    fun providesAddCartUseCase(dispatchers: CoroutineDispatchers, repo: CartRepo, mapper: CartDomainToEntityMapper) =
        AddCartUseCase(
            dispatchers = dispatchers,
            repo = repo,
            cartDomainToEntity = mapper
        )

    @Provides
    fun providesRemoveCartUseCase(dispatchers: CoroutineDispatchers, repo: CartRepo, mapper: CartDomainToEntityMapper) =
        RemoveCartUseCase(
            dispatchers = dispatchers,
            repo = repo,
            cartDomainToEntity = mapper
        )

    @Provides
    fun providesEditCartUseCase(dispatchers: CoroutineDispatchers, repo: CartRepo, mapper: CartDomainToEntityMapper) =
        EditCartUseCase(
            dispatchers = dispatchers,
            repo = repo,
            cartDomainToEntity = mapper
        )
}