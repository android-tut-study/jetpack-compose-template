package com.study.compose.domain.products.mapper

import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.ProductDomain
import com.study.compose.domain.products.model.response.ProductsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductResponseToDomainMapper @Inject constructor(
    private val ratingMapper: RatingResponseToDomainMapper
) : Mapper<ProductsResponse, ProductDomain> {
    override fun invoke(response: ProductsResponse): ProductDomain {
        return ProductDomain(
            id = response.id,
            title = response.title,
            price = response.price,
            description = response.description,
            category = response.category,
            imageUrl = response.imageUrl,
//            rating = ratingMapper(response.rating)
        )
    }
}