package com.study.compose.domain.products.di

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.domain.products.mapper.ProductResponseToDomainMapper
import com.study.compose.domain.products.repository.ProductRepo
import com.study.compose.domain.products.repository.ProductRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://fakestoreapi.com"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providesProductApiService(retrofit: Retrofit): ProductApiService =
        retrofit.create(ProductApiService::class.java)

    @Singleton
    @Provides
    fun providesProductRepository(
        productApiService: ProductApiService,
        dispatcher: CoroutineDispatchers,
        mapper: ProductResponseToDomainMapper
    ): ProductRepo =
        ProductRepoImpl(
            apiService = productApiService,
            dispatcher = dispatcher,
            productDomainMapper = mapper
        )
}