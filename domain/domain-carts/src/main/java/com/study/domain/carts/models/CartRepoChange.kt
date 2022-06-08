package com.study.domain.carts.models

import com.study.compose.core.domain.model.CartDomain

sealed class CartRepoChange {

    fun reduce(cartRepoState: CartRepoState): CartRepoState = when (this) {
        is Add -> cartRepoState.copy(cartChanged = CartChange(cart, CartChangeType.INSERT))
        is Edit -> cartRepoState.copy(cartChanged = CartChange(cart, CartChangeType.UPDATE))
        is Remove -> cartRepoState.copy(cartChanged = CartChange(cart, CartChangeType.REMOVE))
        is AllCarts -> cartRepoState.copy(allCarts = carts)
        Fail -> TODO()
    }


    data class AllCarts(val carts: List<CartDomain>) : CartRepoChange()
    data class Add(val cart: CartDomain) : CartRepoChange()
    data class Remove(val cart: CartDomain) : CartRepoChange()
    data class Edit(val cart: CartDomain) : CartRepoChange()
    object Fail : CartRepoChange()
}

data class CartChange(val cart: CartDomain? = null, val type: CartChangeType? = null)

enum class CartChangeType {
    INSERT,
    REMOVE,
    UPDATE
}

data class CartRepoState(
    val allCarts: List<CartDomain> = emptyList(),
    val cartChanged: CartChange = CartChange(),
) {
    companion object {
        fun initial() = CartRepoState()
    }
}