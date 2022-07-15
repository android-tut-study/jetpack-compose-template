package com.study.domain.carts.di

import android.content.Context
import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.domain.carts.database.CartDatabase
import com.study.domain.carts.mappers.CartEntityToDomainMapper
import com.study.domain.carts.repository.CartRepo
import com.study.domain.carts.repository.CartRepoImpl
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
