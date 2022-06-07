package com.example.domain.carts.mappers

import com.example.domain.carts.models.Cart
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
        amount = p1.amount
    )
}