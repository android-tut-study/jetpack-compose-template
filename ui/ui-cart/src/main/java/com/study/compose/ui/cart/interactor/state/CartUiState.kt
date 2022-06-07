package com.study.compose.ui.cart.interactor.state

import com.study.compose.ui.cart.data.Cart
import com.study.compose.ui.state.UiStatePartialChange

data class CartViewState(
    val expanded: Boolean,
    val carts: List<Cart>,
    val error: Throwable?
) {
    companion object {
        fun initial() = CartViewState(
            expanded = false,
            carts = emptyList(),
            error = null
        )
    }
}

sealed interface CartUIPartialChange : UiStatePartialChange<CartViewState>

sealed class GetCarts() : CartUIPartialChange {
    override fun reduce(vs: CartViewState): CartViewState = when (this) {
        is Data -> vs.copy(carts = carts)
        is Error -> vs.copy(error = error)
    }

    data class Data(val carts: List<Cart>) : GetCarts()
    data class Error(val error: Throwable): GetCarts()
}

enum class CartState {
    Collapsed,
    Expanded,
    Hidden,
}