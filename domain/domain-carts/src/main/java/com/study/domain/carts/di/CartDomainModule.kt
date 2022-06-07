package com.example.domain.carts.di

import android.content.Context
import com.example.domain.carts.database.CartDatabase
import com.example.domain.carts.mappers.CartEntityToDomainMapper
import com.example.domain.carts.repository.CartRepo
import com.example.domain.carts.repository.CartRepoImpl
import com.study.compose.core.dispatcher.CoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartDomainModule {

    @Singleton
    @Provides
    fun provideCartDatabase(@ApplicationContext context: Context): CartDatabase =
        CartDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideCartRepo(
        cartDatabase: CartDatabase,
        coroutineDispatchers: CoroutineDispatchers,
        mapper: CartEntityToDomainMapper
    ): CartRepo = CartRepoImpl(
        cartDatabase = cartDatabase,
        dispatcher = coroutineDispatchers,
        cartEntityToDomain = mapper
    )
}