package com.study.compose.core.di

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.dispatcher.CoroutineDispatchersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesDispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatchers = CoroutineDispatchersImpl()
}