package com.study.compose.domain.products.di

import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.ProductDomain
import com.study.compose.core.domain.model.ProductRatingDomain
import com.study.compose.domain.products.mapper.ProductResponseToDomainMapper
import com.study.compose.domain.products.mapper.RatingResponseToDomainMapper
import com.study.compose.domain.products.model.response.ProductRatingResponse
import com.study.compose.domain.products.model.response.ProductsResponse
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

    @Provides
    fun providesRatingResponseToDomainMapper(): Mapper<ProductRatingResponse, ProductRatingDomain> =
        RatingResponseToDomainMapper()

    @Provides
    fun providesProductResponseToDomainMapper(
        ratingResponseToDomainMapper: Mapper<ProductRatingResponse, ProductRatingDomain>
    ): Mapper<ProductsResponse, ProductDomain> =
        ProductResponseToDomainMapper(ratingResponseToDomainMapper)

    @Singleton
    @Provides
    fun providesProductRepository(
        productApiService: ProductApiService,
        dispatcher: CoroutineDispatchers,
        mapper: Mapper<ProductsResponse, ProductDomain>
    ): ProductRepo =
        ProductRepoImpl(
            apiService = productApiService,
            dispatcher = dispatcher,
            productDomainMapper = mapper
        )
}