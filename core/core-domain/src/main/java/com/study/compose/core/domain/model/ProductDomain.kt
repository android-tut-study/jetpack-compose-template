package com.study.compose.core.domain.model

import com.study.compose.core.domain.DomainModel

data class ProductDomain(
    val id: Long,
    val title: String,
    val price: Float,
    val description: String,
    val category: String,
    val imageUrl: String,
//    val rating: ProductRatingDomain
): DomainModel {

}