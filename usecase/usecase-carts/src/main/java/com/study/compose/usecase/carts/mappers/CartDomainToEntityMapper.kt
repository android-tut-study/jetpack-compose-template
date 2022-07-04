package com.study.compose.usecase.carts.mappers

import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.CartDomain
import com.study.domain.carts.models.Cart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartDomainToEntityMapper @Inject constructor() : Mapper<CartDomain, Cart> {
    override fun invoke(p1: CartDomain): Cart = Cart(
        productId = p1.productId,
        title = p1.title,
        description = p1.description,
        price = p1.price,
        amount = p1.amount,
        size = p1.size,
        color = p1.color,
        imageUrl = p1.imageUrl,
        category = p1.category,
    ).apply {
        id = p1.id
    }
}