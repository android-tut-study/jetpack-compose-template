package com.example.domain.carts.repository

import com.example.domain.carts.database.CartDatabase
import com.example.domain.carts.models.Cart
import com.example.domain.carts.models.CartRepoChange
import com.example.domain.carts.models.CartRepoState
import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.CartDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class CartRepoImpl(
    private val cartDatabase: CartDatabase,
    private val dispatcher: CoroutineDispatchers,
    private val cartEntityToDomain: Mapper<Cart, CartDomain>
) : CartRepo {

    private val _cartRepoChange = MutableSharedFlow<CartRepoChange>()
    val cartRepoState: StateFlow<CartRepoState>

    init {
        val initState = CartRepoState.initial()
        cartRepoState = MutableStateFlow(initState)
        _cartRepoChange
            .scan(initState) { state, change -> change.reduce(state) }
            .onEach { cartRepoState.value = it }
            .launchIn(CoroutineScope(dispatcher.io))
    }

    override fun getCarts(): Flow<List<Cart>> = flow {
        cartDatabase.cartDao().allCarts().onEach { emit(it) }
    }.flowOn(dispatcher.io)

    override suspend fun addCart(cart: Cart) {
        val result = cartDatabase.cartDao().addToCarts(cart)
        if (result > 0) {
            _cartRepoChange.emit(CartRepoChange.Add(cart = cartEntityToDomain(cart)))
        }
    }

    override suspend fun removeCart(cart: Cart) {
        cartDatabase.cartDao().deleteCarts(cart)
        val result = cartDatabase.cartDao().deleteCarts(cart)
        if (result > 0) {
            _cartRepoChange.emit(CartRepoChange.Remove(cart = cartEntityToDomain(cart)))
        }
    }

    override suspend fun removeAllCarts() {
        cartDatabase.clearAllTables()
    }

    override suspend fun editCart(cart: Cart) {
        cartDatabase.cartDao().editCarts(cart)
    }

}

