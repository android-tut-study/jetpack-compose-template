package com.study.compose.domain.products.mapper

import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.ProductDomain
import com.study.compose.core.domain.model.ProductRatingDomain
import com.study.compose.domain.products.model.response.ProductRatingResponse
import com.study.compose.domain.products.model.response.ProductsResponse

class ProductResponseToDomainMapper(
    private val ratingMapper: Mapper<ProductRatingResponse, ProductRatingDomain>
) : Mapper<ProductsResponse, ProductDomain> {
    override fun invoke(response: ProductsResponse): ProductDomain {
        return ProductDomain(
            id = response.id,
            title = response.title,
            price = response.price,
            description = response.description,
            category = response.category,
            imageUrl = response.imageUrl,
            rating = ratingMapper(response.rating)
        )
    }
}