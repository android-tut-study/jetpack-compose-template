package com.study.compose.core.domain.model

import com.study.compose.core.domain.DomainModel

data class ProductRatingDomain(
    val rate: Float,
    val count: Int
) : DomainModel
