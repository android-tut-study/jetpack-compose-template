package com.study.domain.carts.mappers

import com.study.domain.carts.models.Cart
import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.CartDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartEntityToDomainMapper @Inject constructor() : Mapper<Cart, CartDomain> {
    override fun invoke(p1: Cart): CartDomain = CartDomain(
        productId = p1.productId,
        title = p1.title,
        description = p1.description,
        price = p1.price,
        amount = p1.amount,
        size = p1.size,
        color = p1.color,
        imageUrl = p1.imageUrl,
        category = p1.category,
        id = p1.id
    )
}