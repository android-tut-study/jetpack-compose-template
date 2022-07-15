package com.study.domain.carts.repository

import androidx.paging.PagingSource
import com.study.compose.core.dispatcher.CoroutineDispatchers
import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.CartDomain
import com.study.domain.carts.database.CartDatabase
import com.study.domain.carts.models.Cart
import com.study.domain.carts.models.CartRepoChange
import com.study.domain.carts.models.CartRepoState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

class CartRepoImpl(
    private val cartDatabase: CartDatabase,
    private val dispatcher: CoroutineDispatchers,
    private val cartEntityToDomain: Mapper<Cart, CartDomain>
) : CartRepo {

    private val _cartRepoChange = MutableSharedFlow<CartRepoChange>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val cartRepoState: StateFlow<CartRepoState>

    init {
        val initState = CartRepoState.initial()
        cartRepoState = MutableStateFlow(initState)
        _cartRepoChange
            .scan(initState) { state, change -> change.reduce(state) }
            .onEach { cartRepoState.value = it }
            .launchIn(CoroutineScope(dispatcher.io))
    }

    override fun getCarts(): Flow<List<CartDomain>> = flow {
        cartDatabase.cartDao().allCarts()
            .onEach { carts ->
                val cartDomains = carts.map(cartEntityToDomain)
                emit(cartDomains)
                _cartRepoChange.emit(CartRepoChange.AllCarts(carts = cartDomains))
            }
            .collect()
    }.flowOn(dispatcher.io)

    override suspend fun addCart(cart: Cart): Long {
        val result = cartDatabase.cartDao().addToCarts(cart)
        if (result > 0) {
            _cartRepoChange.emit(CartRepoChange.Add(cart = cartEntityToDomain(cart)))
        }
        return result
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

    override fun getCartRepoState(): Flow<CartRepoState> = flow {
        cartRepoState
            .onEach { emit(it) }
            .collect()
    }

    override fun allCartsPagingSource(): PagingSource<Int, Cart> =
        cartDatabase.cartDao().lazyAllCarts()
}

