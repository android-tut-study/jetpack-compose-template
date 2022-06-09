package com.study.domain.carts.repository

import android.util.Log
import com.study.domain.carts.database.CartDatabase
import com.study.domain.carts.models.Cart
import com.study.domain.carts.models.CartRepoChange
import com.study.domain.carts.models.CartRepoState
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
        Log.e("ANNX", "ADd Cart Result $result")
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

}

