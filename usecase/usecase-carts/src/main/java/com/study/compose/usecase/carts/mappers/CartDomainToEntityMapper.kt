package com.study.compose.usecase.carts.mappers

import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.CartDomain
import com.study.domain.carts.models.Cart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartDomainToEntityMapper @Inject constructor(): Mapper<CartDomain, Cart> {
    override fun invoke(p1: CartDomain): Cart = Cart(
        title = p1.title,
        description = p1.description,
        productId = p1.productId,
        price = p1.price,
        amount = p1.amount
    )
}