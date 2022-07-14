package com.study.compose.ui.cart.interactor.state

import com.study.compose.ui.cart.data.Cart
import com.study.compose.ui.state.UiStatePartialChange

data class CartViewState(
    val expanded: Boolean,
    val cartsChange: List<Cart>,
    val error: Throwable?
) {
    companion object {
        fun initial() = CartViewState(
            expanded = false,
            cartsChange = emptyList(),
            error = null
        )
    }
}

sealed interface CartUIPartialChange : UiStatePartialChange<CartViewState>

sealed class CartsChange : CartUIPartialChange {
    override fun reduce(vs: CartViewState): CartViewState = when (this) {
        is CartAdded -> vs.copy(cartsChange = vs.cartsChange + cart)
        is CartRemoved -> vs.copy(cartsChange = vs.cartsChange - cart)
        is CartUpdated -> {
            val updatedCarts =
                vs.cartsChange.map { if (it.productId == cart.productId) cart else it }
            vs.copy(cartsChange = updatedCarts)
        }
    }

    data class CartAdded(val cart: Cart) : CartsChange()
    data class CartUpdated(val cart: Cart) : CartsChange()
    data class CartRemoved(val cart: Cart) : CartsChange()
}

enum class CartState {
    Collapsed,
    Expanded,
    Hidden,
}